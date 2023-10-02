package com.m2l.meta.service;

import com.m2l.meta.dto.ItemBoughtDto;
import com.m2l.meta.dto.ItemPlaceDto;
import com.m2l.meta.entity.MyRoomEntity;
import com.m2l.meta.exceptions.MamException;

import java.util.List;

public interface MyRoomService {

	MyRoomEntity getMyRoomById(Long id);
	
	void saveMyRoom(MyRoomEntity myRoomEntity);

	Integer expandCurrentUserRoom() throws MamException;

	ItemPlaceDto placeOrReplaceItem(ItemPlaceDto placeDto) throws MamException;

	List<ItemPlaceDto> getAllPlacedItemCurrentRoom() throws MamException;

	ItemBoughtDto getBoughtItem() throws MamException;

	String getRoomKey() throws MamException;

	String generateRoomKey() throws MamException;

	Boolean checkRoomKey(Long characterId, String roomKey) throws MamException;
}
