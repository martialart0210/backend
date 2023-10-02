package com.m2l.meta.repository;

import com.m2l.meta.entity.ChatHistory;
import com.m2l.meta.enum_class.ChatType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ChatHistoryRepo extends JpaRepository<ChatHistory, Long> {
    ChatHistory findChatHistoryByChatDateAndType(LocalDate date, ChatType type);

    Page<ChatHistory> findChatHistoriesByTypeAndObjectIdOrderByChatDateDesc(ChatType type, Long objectId, Pageable pageable);

    @Query(value = "select * from chat_history where CHAT_TYPE = :type and ( (OBJECT_ID = :fromId and SENT_TO = :toId)" +
            " or (OBJECT_ID = :toId and SENT_TO = :fromId) ) ORDER BY CHAT_DATE DESC ", nativeQuery = true)
    Page<ChatHistory> getFriendChatLog(@Param("type") Integer type, @Param("fromId") Long fromId, @Param("toId") Long toId, Pageable pageable);

    @Modifying
    @Query(value = "update CHAT_HISTORY SET CHAT_LOG = JSON_MERGE_PRESERVE(CHAT_LOG,:chatLog) where ID = :id", nativeQuery = true)
    Integer updateChatHistory(@Param("chatLog") String chatLog, @Param("id") Long id);
}
