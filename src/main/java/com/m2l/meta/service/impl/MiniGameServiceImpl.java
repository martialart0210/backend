package com.m2l.meta.service.impl;

import com.m2l.meta.dto.GameDto;
import com.m2l.meta.dto.PageResDto;
import com.m2l.meta.dto.RecordPageRequest;
import com.m2l.meta.entity.MiniGame;
import com.m2l.meta.entity.MiniGameRecord;
import com.m2l.meta.entity.User;
import com.m2l.meta.entity.UserCharacter;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.repository.CharacterRepository;
import com.m2l.meta.repository.MiniGameRecordRepo;
import com.m2l.meta.repository.MiniGameRepo;
import com.m2l.meta.repository.UserRepository;
import com.m2l.meta.service.MiniGameService;
import com.m2l.meta.utils.CommonConstants;
import com.m2l.meta.utils.PageUtils;
import com.m2l.meta.utils.UserAuthUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MiniGameServiceImpl implements MiniGameService {

    private UserAuthUtils authUtils;

    private CharacterRepository characterRepository;
    private UserRepository userRepository;
    private MiniGameRecordRepo gameRecordRepo;

    private MiniGameRepo miniGameRepo;
    private PageUtils pageUtils;


    @Autowired
    public MiniGameServiceImpl(UserAuthUtils authUtils, CharacterRepository characterRepository,
                               UserRepository userRepository, MiniGameRecordRepo gameRecordRepo,
                               PageUtils pageUtils, MiniGameRepo miniGameRepo) {
        this.authUtils = authUtils;
        this.characterRepository = characterRepository;
        this.userRepository = userRepository;
        this.gameRecordRepo = gameRecordRepo;
        this.pageUtils = pageUtils;
        this.miniGameRepo = miniGameRepo;
    }

    @Override
    public List<GameDto> getGameInfo() throws MamException {
        Optional<User> userOptional = userRepository.findUsersByUsername(authUtils.getUserInfoFromReq().getUsername());
        if (userOptional.isEmpty()) {
            throw new MamException(CommonConstants.MessageError.ER021, null);
        }
        User user = userOptional.get();
        List<MiniGameRecord> gameRecords = user.getCharacter().getGameRecords()
                .stream()
                .filter(miniGameRecord -> !miniGameRecord.getUpdatedAt().toLocalDate().isEqual(LocalDate.now()))
                .toList().stream().peek(miniGameRecord -> {
                    miniGameRecord.setIsClaimed(false);
                    miniGameRecord.setDailyRecord(0);
                    miniGameRecord.setUpdatedAt(LocalDateTime.now());
                }).toList();
        if (!gameRecords.isEmpty()) {
            gameRecordRepo.saveAll(gameRecords);
            user = userRepository.findById(user.getId()).orElseThrow();
        }
        return user.getCharacter().getGameRecords().stream().map(MiniGameServiceImpl::getGameDto).collect(Collectors.toList());

    }

    @Override
    public PageResDto<GameDto> findBestRecordDaily(RecordPageRequest recordPageRequest) throws MamException {
        Pageable sizePagination = PageRequest.of(recordPageRequest.page(), recordPageRequest.size());
        return pageUtils.convertPageEntityToDTO(gameRecordRepo.findBestRecordDaily(LocalDateTime.now(), recordPageRequest.gameId(), sizePagination), GameDto.class);
    }

    @Override
    public List<GameDto> getGameInfoOfOther(Long characterId) throws MamException {
        Optional<UserCharacter> characterOptional = characterRepository.findById(characterId);
        if (characterOptional.isEmpty()) {
            throw new MamException(CommonConstants.MessageError.ER021, null);
        }
        UserCharacter character = characterOptional.get();
        return character.getGameRecords().stream().map(MiniGameServiceImpl::getGameDto).collect(Collectors.toList());
    }

    private static GameDto getGameDto(MiniGameRecord miniGameRecord) {
        return ObjectUtils.isEmpty(miniGameRecord) ?
                null : GameDto.builder()
                .gameId(miniGameRecord.getMiniGame().getId())
                .gameName(miniGameRecord.getMiniGame().getName())
                .dailyRecord(miniGameRecord.getDailyRecord())
                .bestRecord(miniGameRecord.getBestRecord())
                .reward(miniGameRecord.getMiniGame().getReward().intValue())
                .isClaimed(miniGameRecord.getIsClaimed())
                .recordReward(miniGameRecord.getMiniGame().getRecordReward())
                .build();
    }

    @Override
    public GameDto saveRecord(GameDto gameDto) throws MamException {
        Optional<User> userOptional = userRepository.findUsersByUsername(authUtils.getUserInfoFromReq().getUsername());
        if (userOptional.isEmpty()) {
            throw new MamException(CommonConstants.MessageError.ER021, null);
        }
        MiniGameRecord record = gameRecordRepo.findAllByCharacter_User_IdAndAndMiniGame_Id(userOptional.get().getId(), gameDto.getGameId());
        if (record != null) {
            if (record.getDailyRecord() <= gameDto.getDailyRecord()) {
                record.setDailyRecord(gameDto.getDailyRecord());
            }
            if (record.getBestRecord() < gameDto.getDailyRecord()) {
                record.setBestRecord(gameDto.getDailyRecord());
                record.setBestAt(LocalDateTime.now());
            }
            record.setUpdatedAt(LocalDateTime.now());
            record = gameRecordRepo.save(record);
        } else {
            MiniGame miniGame = miniGameRepo.findById(gameDto.getGameId()).orElseThrow();
            record = gameRecordRepo.save(MiniGameRecord.builder()
                    .miniGame(miniGame)
                    .character(userOptional.get().getCharacter())
                    .bestAt(LocalDateTime.now())
                    .bestRecord(gameDto.getDailyRecord())
                    .dailyRecord(gameDto.getDailyRecord())
                    .updatedAt(LocalDateTime.now())
                    .isClaimed(false)
                    .build());
        }
        return getGameDto(record);
    }

    @Override
    public GameDto getReward(Long gameId) throws MamException {
        Optional<User> userOptional = userRepository.findUsersByUsername(authUtils.getUserInfoFromReq().getUsername());
        if (userOptional.isEmpty()) {
            throw new MamException(CommonConstants.MessageError.ER021, null);
        }
        MiniGameRecord record = gameRecordRepo.findAllByCharacter_User_IdAndAndMiniGame_Id(userOptional.get().getId(), gameId);
        if (record.getIsClaimed()) {
            throw new MamException(CommonConstants.MessageError.ER049, null);
        }
        if (record.getDailyRecord() < record.getMiniGame().getRecordReward()) {
            throw new MamException(CommonConstants.MessageError.ER050, null);
        }
        UserCharacter character = userOptional.get().getCharacter();
        character.setGold(character.getGold().add(record.getMiniGame().getReward()));
        characterRepository.save(character);
        record.setIsClaimed(true);
        record.setUpdatedAt(LocalDateTime.now());
        return getGameDto(gameRecordRepo.save(record));
    }
}
