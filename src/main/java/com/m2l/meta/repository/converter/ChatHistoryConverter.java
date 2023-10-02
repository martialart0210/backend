package com.m2l.meta.repository.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2l.meta.dto.ChatDto;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@Converter
public class ChatHistoryConverter implements AttributeConverter<List<ChatDto>, String> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<ChatDto> chatDtoList) {
        try {
            return objectMapper.writeValueAsString(chatDtoList);
        } catch (JsonProcessingException ex) {
            return null;
        }
    }

    @Override
    public List<ChatDto> convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, List.class);
        } catch (IOException ex) {
            // logger.error("Unexpected IOEx decoding json from database: " + dbData);
            return null;
        }
    }
}
