package com.mar.meta.service;

import com.mar.meta.dto.RoomDto;
import com.mar.meta.exceptions.MamException;

public interface RoomService {
    RoomDto expandRoom(int level) throws MamException;
}
