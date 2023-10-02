package com.m2l.meta.service;

import com.m2l.meta.dto.RoomDto;
import com.m2l.meta.exceptions.MamException;

public interface RoomService {
    RoomDto expandRoom(int level) throws MamException;
}
