package com.m2l.meta.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2l.meta.dto.PageResDto;
import com.m2l.meta.entity.ChatHistory;
import com.m2l.meta.entity.User;
import com.m2l.meta.enum_class.ChatType;
import com.m2l.meta.repository.ChatHistoryRepo;
import com.m2l.meta.repository.UserRepository;
import com.m2l.meta.service.ChatPageRecord;
import com.m2l.meta.service.ChatService;
import com.m2l.meta.utils.PageUtils;
import com.m2l.meta.utils.UserAuthUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    public ChatServiceImpl(ChatHistoryRepo chatHistoryRepo, UserRepository userRepository, PageUtils pageUtils, ObjectMapper objectMapper, UserAuthUtils authUtils) {
        this.chatHistoryRepo = chatHistoryRepo;
        this.userRepository = userRepository;
        this.pageUtils = pageUtils;
        this.objectMapper = objectMapper;
        this.authUtils = authUtils;
    }

    private ChatHistoryRepo chatHistoryRepo;
    private UserRepository userRepository;
    private PageUtils pageUtils;

    private ObjectMapper objectMapper;
    private UserAuthUtils authUtils;

    @Override
    public ChatHistory getChatByDate(LocalDate date, ChatType type) {
        return chatHistoryRepo.findChatHistoryByChatDateAndType(date, type);
    }

    @Override
    public PageResDto<ChatHistory> getChatPagination(ChatPageRecord chatPageRecord) {
        Pageable sizePagination = PageRequest.of(chatPageRecord.page(), chatPageRecord.size());
        Optional<User> currentUser = Optional.empty();
        try {
            currentUser = userRepository.findUsersByUsername(authUtils.getCurrentUserInfo().getUsername());
            if (currentUser.isEmpty()) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (ChatType.valueOf(chatPageRecord.type()).equals(ChatType.DOJO)) {
            return pageUtils.convertPageEntityToDTO(
                    chatHistoryRepo.findChatHistoriesByTypeAndObjectIdOrderByChatDateDesc(ChatType.valueOf(chatPageRecord.type()),
                            currentUser.get().getCharacter().getDojoMember().getDojo().getId(),
                            sizePagination),
                    ChatHistory.class);
        }
        return pageUtils.convertPageEntityToDTO(
                chatHistoryRepo.getFriendChatLog(ChatType.valueOf(chatPageRecord.type()).getValue(),
                        currentUser.get().getCharacter().getCharacterId(),
                        chatPageRecord.friendId(),
                        sizePagination),
                ChatHistory.class);
    }

    @Override
    @Transactional
    public ChatHistory saveChatLog(ChatHistory chatHistory) {
//        Check today history
        ChatHistory todayHistory = Optional.ofNullable(this.getChatByDate(LocalDate.now(), chatHistory.getType())).orElse(new ChatHistory());
        if (todayHistory.getId() != null && todayHistory.getId() > 0) {
            try {
                chatHistoryRepo.updateChatHistory(objectMapper.writeValueAsString(chatHistory.getChatLog()), todayHistory.getId());
                return todayHistory;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return chatHistoryRepo.save(ChatHistory.builder()
                .sentTo(chatHistory.getSentTo())
                .objectId(chatHistory.getObjectId())
                .chatDate(LocalDate.now())
                .type(chatHistory.getType())
                .chatLog(chatHistory.getChatLog())
                .build());
    }
}
