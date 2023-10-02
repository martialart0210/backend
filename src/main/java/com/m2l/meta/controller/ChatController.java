package com.m2l.meta.controller;

import com.m2l.meta.dto.PageResDto;
import com.m2l.meta.entity.ChatHistory;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.service.ChatPageRecord;
import com.m2l.meta.service.ChatService;
import com.m2l.meta.utils.CommonConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/chat")
@Tag(name = "chat-log-api")
public class ChatController extends BaseController {

    @Autowired
    private ChatService chatService;

    @Operation(summary = "Get Chat Log Pagination")
    @GetMapping()
    ResponseEntity<?> getChatLogToday(ChatPageRecord chatPageRecord) {
        PageResDto<ChatHistory> chatHistory = chatService.getChatPagination(chatPageRecord);
        return success(CommonConstants.MessageSuccess.SC001, chatHistory, null);
    }

    @Operation(summary = "Save Chat Log")
    @PostMapping()
    ResponseEntity<?> saveChatLog(@RequestBody ChatHistory chatHistory) {
        ChatHistory updatedChat = chatService.saveChatLog(chatHistory);
        return success(CommonConstants.MessageSuccess.SC003, updatedChat, null);
    }
}
