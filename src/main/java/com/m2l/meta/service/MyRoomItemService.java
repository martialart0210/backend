package com.m2l.meta.service;

import com.m2l.meta.entity.MyRoomItem;
import com.m2l.meta.exceptions.MamException;

import java.util.List;

public interface MyRoomItemService {

    List<MyRoomItem> getAllItem() throws MamException;
}
