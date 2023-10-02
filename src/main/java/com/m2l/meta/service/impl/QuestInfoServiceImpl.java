package com.m2l.meta.service.impl;

import com.m2l.meta.dto.QuestProgressDto;
import com.m2l.meta.dto.UserAuthDetected;
import com.m2l.meta.entity.CharacterQuestDetail;
import com.m2l.meta.entity.User;
import com.m2l.meta.entity.UserCharacter;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.repository.CharacterQuestRepository;
import com.m2l.meta.repository.CharacterRepository;
import com.m2l.meta.service.QuestInfoService;
import com.m2l.meta.service.UserService;
import com.m2l.meta.utils.CommonConstants;
import com.m2l.meta.utils.UserAuthUtils;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestInfoServiceImpl implements QuestInfoService {

    private CharacterQuestRepository characterQuestRepository;

    private CharacterRepository characterRepository;

    private UserAuthUtils authUtils;

    private UserService userService;

    @Autowired
    public QuestInfoServiceImpl(CharacterQuestRepository characterQuestRepository,
                                UserAuthUtils authUtils,
                                CharacterRepository characterRepository,
                                UserService userService) {
        this.characterQuestRepository = characterQuestRepository;
        this.authUtils = authUtils;
        this.characterRepository = characterRepository;
        this.userService = userService;
    }

    @Override
    public QuestProgressDto getQuestInfo(Long questId) throws MamException {
        UserAuthDetected userAuthDetected = authUtils.getUserInfoFromReq();
        CharacterQuestDetail questDetail = characterQuestRepository.getQuestDetailForCurrentUser(userAuthDetected.getUsername(), questId);
        return convertToDto(questDetail);
    }

    @Override
    @Transactional
    public QuestProgressDto getRewardQuest(Long questId) throws MamException {
        UserAuthDetected userAuthDetected = authUtils.getUserInfoFromReq();
        CharacterQuestDetail questDetail = characterQuestRepository.getQuestDetailForCurrentUser(userAuthDetected.getUsername(), questId);
        if (questDetail.getPerformedCount() == questDetail.getMaxPerformed()) {
            questDetail.setFinished(true);
            UserCharacter character = questDetail.getCharacter();
            character.setGold(character.getGold().add(BigInteger.valueOf(questDetail.getQuestInfo().getReward())));
            character = characterRepository.save(character);
            questDetail.setCharacter(character);
            questDetail.setUpdatedAt(LocalDate.now());
            questDetail = characterQuestRepository.save(questDetail);
        }
        return convertToDto(questDetail);
    }

    @Override
    @Transactional
    public QuestProgressDto acceptQuest(Long questId) throws MamException {
        UserAuthDetected userAuthDetected = authUtils.getUserInfoFromReq();
        CharacterQuestDetail questDetail = characterQuestRepository.getQuestDetailForCurrentUserAcceptable
                (userAuthDetected.getUsername(), questId, false, null, false);
        if (ObjectUtils.isEmpty(questDetail)) {
            throw new MamException(CommonConstants.MessageError.ER017, null);
        }
        questDetail.setAccept(true);
        questDetail.setCompleted(false);
        questDetail.setUpdatedAt(LocalDate.now());
        questDetail = characterQuestRepository.save(questDetail);
        return convertToDto(questDetail);
    }

    @Override
    public List<QuestProgressDto> getAllQuest() throws MamException {
        UserAuthDetected userAuthDetected = authUtils.getUserInfoFromReq();
        User user = userService.getUserByUsername(userAuthDetected.getUsername());
        UserCharacter userCharacter = characterRepository.findAllByUser_Id(user.getId());
        return characterQuestRepository.findAllByCharacter_CharacterId(userCharacter.getCharacterId()).stream().map(detail ->
                QuestProgressDto.builder()
                        .questId(detail.getQuestInfo().getQuestInfoID())
                        .questName(detail.getQuestInfo().questName)
                        .questDescription(detail.getQuestInfo().getQuestDescription())
                        .isCompleted(detail.isCompleted())
                        .isAccept(detail.isAccept())
                        .isFinished(detail.isFinished())
                        .maxPerformed(detail.getMaxPerformed())
                        .performedCount(detail.getPerformedCount())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuestProgressDto completedQuest(Long questId) throws MamException {
        UserAuthDetected userAuthDetected = authUtils.getUserInfoFromReq();
        CharacterQuestDetail questDetail = characterQuestRepository.getQuestDetailForCurrentUserAcceptable
                (userAuthDetected.getUsername(), questId, true, false, false);
        questDetail.setAccept(false);
        questDetail.setCompleted(true);
        questDetail.setPerformedCount(questDetail.getPerformedCount() + 1);
        questDetail.setUpdatedAt(LocalDate.now());
        questDetail = characterQuestRepository.save(questDetail);
//        UserCharacter character = questDetail.getCharacter();
//        character.setGold(character.getGold().add(BigInteger.valueOf(questDetail.getQuestInfo().getReward())));
//        characterRepository.save(character);
        return convertToDto(questDetail);
    }

    QuestProgressDto convertToDto(CharacterQuestDetail detail) {
        QuestProgressDto dto = new QuestProgressDto();
        BeanUtils.copyProperties(detail, dto);
        BeanUtils.copyProperties(detail.getQuestInfo(), dto);
        dto.setQuestId(detail.getQuestInfo().getQuestInfoID());
        return dto;
    }


}
