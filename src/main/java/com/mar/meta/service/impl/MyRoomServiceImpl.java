package com.mar.meta.service.impl;

import com.mar.meta.entity.MyRoomEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.mar.meta.repository.MyRoomRepository;
import com.mar.meta.service.MyRoomService;
import org.springframework.stereotype.Service;

@Service
public class MyRoomServiceImpl implements MyRoomService{

	@Autowired
	MyRoomRepository myRoomRepository;
	
	@Override
	public MyRoomEntity getMyRoomById(Long id) {
		
		return myRoomRepository.findById(id).get();
	}

	@Override
	public void saveMyRoom(MyRoomEntity myRoomEntity) {
		myRoomRepository.save(myRoomEntity);
		
	}

}
