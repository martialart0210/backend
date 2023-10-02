package com.mar.meta.service.impl;

import com.mar.meta.dto.QuestProgressDto;
import com.mar.meta.dto.UserAuthDetected;
import com.mar.meta.entity.CharacterQuestDetail;
import com.mar.meta.entity.UserCharacter;
import com.mar.meta.exceptions.MamException;
import com.mar.meta.repository.CharacterQuestRepository;
import com.mar.meta.repository.CharacterRepository;
import com.mar.meta.service.QuestInfoService;
import com.mar.meta.utils.CommonConstants;
import com.mar.meta.utils.UserAuthUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class QuestInfoServiceImpl implements QuestInfoService {

    private CharacterQuestRepository characterQuestRepository;

    private CharacterRepository characterRepository;

    private UserAuthUtils authUtils;

    @Autowired
    public QuestInfoServiceImpl(CharacterQuestRepository characterQuestRepository,
                                UserAuthUtils authUtils,
                                CharacterRepository characterRepository) {
        this.characterQuestRepository = characterQuestRepository;
        this.authUtils = authUtils;
        this.characterRepository = characterRepository;
    }

    @Override
    public QuestProgressDto getQuestInfo(Long questId) throws MamException {
        UserAuthDetected userAuthDetected = authUtils.getUserInfoFromReq();
        CharacterQuestDetail questDetail = characterQuestRepository.getQuestDetailForCurrentUser(userAuthDetected.getUsername(), questId);
        return convertToDto(questDetail);
    }

    @Override
    public QuestProgressDto getRewardQuest(Long questId) throws MamException {
        UserAuthDetected userAuthDetected = authUtils.getUserInfoFromReq();
        CharacterQuestDetail questDetail = characterQuestRepository.getQuestDetailForCurrentUser(userAuthDetected.getUsername(), questId);
        if (questDetail.getPerformedCount() == questDetail.getMaxPerformed()) {
            questDetail.setFinished(true);
            UserCharacter character = questDetail.getCharacter();
            character.setGold(character.getGold().add(BigInteger.valueOf(questDetail.getQuestInfo().getReward())));
            character = characterRepository.save(character);
            questDetail.setCharacter(character);
            questDetail = characterQuestRepository.save(questDetail);
        }
        return convertToDto(questDetail);
    }

    @Override
    public QuestProgressDto acceptQuest(Long questId) throws MamException {
        UserAuthDetected userAuthDetected = authUtils.getUserInfoFromReq();
        CharacterQuestDetail questDetail = characterQuestRepository.getQuestDetailForCurrentUserAcceptable
                (userAuthDetected.getUsername(), questId, false, null, false);
        if (ObjectUtils.isEmpty(questDetail)) {
            throw new MamException(CommonConstants.MessageError.ER017, null);
        }
        questDetail.setAccept(true);
        questDetail.setCompleted(false);
        questDetail = characterQuestRepository.save(questDetail);
        return convertToDto(questDetail);
    }

    @Override
    public QuestProgressDto completedQuest(Long questId) throws MamException {
        UserAuthDetected userAuthDetected = authUtils.getUserInfoFromReq();
        CharacterQuestDetail questDetail = characterQuestRepository.getQuestDetailForCurrentUserAcceptable
                (userAuthDetected.getUsername(), questId, true, false, false);
        questDetail.setAccept(false);
        questDetail.setCompleted(true);
        questDetail.setPerformedCount(questDetail.getPerformedCount() + 1);
        questDetail = characterQuestRepository.save(questDetail);
        return convertToDto(questDetail);
    }

    QuestProgressDto convertToDto(CharacterQuestDetail detail) {
        QuestProgressDto dto = new QuestProgressDto();
        BeanUtils.copyProperties(detail, dto);
        BeanUtils.copyProperties(detail.getQuestInfo(), dto);
        return dto;
    }


}
