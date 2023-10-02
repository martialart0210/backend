package com.m2l.meta.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.m2l.meta.dto.ChatDto;
import com.m2l.meta.enum_class.ChatType;
import com.m2l.meta.repository.converter.ChatHistoryConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "CHAT_HISTORY")
@Table(name = "chat_history")
public class ChatHistory {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "OBJECT_ID", nullable = false)
    private Long objectId;

    @Column(name = "SENT_TO")
    private Long sentTo;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "CHAT_TYPE")
    private ChatType type;

    @Column(name = "CHAT_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING,  pattern = "yyyy-MM-dd")
    private LocalDate chatDate = LocalDate.now();

    @Column(name = "CHAT_LOG",columnDefinition = "json")
    @Convert(converter = ChatHistoryConverter.class)
    private List<ChatDto> chatLog;

}
