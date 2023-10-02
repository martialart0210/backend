package com.m2l.meta.service;

import com.m2l.meta.enum_class.ChatType;

public record ChatPageRecord(int page, int size, String type, Long objectId, Long friendId) {
}