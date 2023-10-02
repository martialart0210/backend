package com.m2l.meta.service;

import com.m2l.meta.dto.PageResDto;
import com.m2l.meta.entity.ChatHistory;
import com.m2l.meta.enum_class.ChatType;

import java.time.LocalDate;

public interface ChatService {
    ChatHistory getChatByDate(LocalDate date, ChatType type);

    PageResDto<ChatHistory> getChatPagination(ChatPageRecord chatPageRecord);

    ChatHistory saveChatLog(ChatHistory chatHistory);
}
