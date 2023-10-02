package com.m2l.meta.controller;

import com.m2l.meta.dto.QuestProgressDto;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.service.QuestInfoService;
import com.m2l.meta.utils.CommonConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/quest")
@Tag(name = "quest-api")
public class QuestController extends BaseController {
    private QuestInfoService questInfoService;

    @Autowired
    public QuestController(QuestInfoService questInfoService) {
        this.questInfoService = questInfoService;
    }

    @GetMapping("/progress/{questId}")
    public ResponseEntity<?> getQuestProgress(@PathVariable("questId") Long questId) {
        try {
            QuestProgressDto dto = questInfoService.getQuestInfo(questId);
            return success(CommonConstants.MessageSuccess.SC001, dto, null);
        } catch (MamException e) {
            return failed(CommonConstants.MessageError.ER017, null);
        }
    }

    @GetMapping("/completed/{questId}")
    public ResponseEntity<?> completeQuest(@PathVariable("questId") Long questId) {
        try {
            QuestProgressDto dto = questInfoService.completedQuest(questId);
            return success(CommonConstants.MessageSuccess.SC001, dto, null);
        } catch (MamException e) {
            return failed(CommonConstants.MessageError.ER017, null);
        }
    }

    @GetMapping("/accept/{questId}")
    public ResponseEntity<?> acceptQuest(@PathVariable("questId") Long questId) {
        try {
            QuestProgressDto dto = questInfoService.acceptQuest(questId);
            return success(CommonConstants.MessageSuccess.SC001, dto, null);
        } catch (MamException e) {
            return failed(CommonConstants.MessageError.ER017, null);
        }
    }

    @GetMapping("/reward/{questId}")
    public ResponseEntity<?> rewardQuest(@PathVariable("questId") Long questId) {
        try {
            QuestProgressDto dto = questInfoService.getRewardQuest(questId);
            return success(CommonConstants.MessageSuccess.SC001, dto, null);
        } catch (MamException e) {
            return failed(CommonConstants.MessageError.ER017, null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllQuest() {
        try {
            return success(CommonConstants.MessageSuccess.SC001,questInfoService.getAllQuest(),null);
        } catch (MamException e) {
            return failed(CommonConstants.MessageError.ER017, null);
        }
    }
}
