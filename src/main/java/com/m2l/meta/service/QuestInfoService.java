package com.m2l.meta.service;

import com.m2l.meta.dto.QuestProgressDto;
import com.m2l.meta.exceptions.MamException;

import java.util.List;

public interface QuestInfoService {
    QuestProgressDto getQuestInfo(Long questId) throws MamException;

    QuestProgressDto completedQuest(Long questId) throws MamException;

    QuestProgressDto getRewardQuest(Long questId) throws MamException;

    QuestProgressDto acceptQuest(Long questId) throws MamException;

    List<QuestProgressDto> getAllQuest() throws MamException;
}
