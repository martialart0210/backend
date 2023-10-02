package com.mar.meta.service;

import com.mar.meta.dto.QuestProgressDto;
import com.mar.meta.exceptions.MamException;

public interface QuestInfoService {
    QuestProgressDto getQuestInfo(Long questId) throws MamException;

    QuestProgressDto completedQuest(Long questId) throws MamException;

    QuestProgressDto getRewardQuest(Long questId) throws MamException;

    QuestProgressDto acceptQuest(Long questId) throws MamException;
}
