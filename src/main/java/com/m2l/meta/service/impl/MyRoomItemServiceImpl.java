package com.m2l.meta.service.impl;

import com.m2l.meta.entity.MyRoomItem;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.repository.MyRoomItemRepository;
import com.m2l.meta.service.MyRoomItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyRoomItemServiceImpl implements MyRoomItemService {

    private MyRoomItemRepository roomItemRepository;

    @Autowired
    public MyRoomItemServiceImpl(MyRoomItemRepository roomItemRepository) {
        this.roomItemRepository = roomItemRepository;
    }

    @Override
    public List<MyRoomItem> getAllItem() throws MamException {
        return null;
    }
}
