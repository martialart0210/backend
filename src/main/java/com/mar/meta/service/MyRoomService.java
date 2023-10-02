package com.mar.meta.service;

import com.mar.meta.entity.MyRoomEntity;

public interface MyRoomService {

	MyRoomEntity getMyRoomById(Long id);
	
	void saveMyRoom(MyRoomEntity myRoomEntity);
}
