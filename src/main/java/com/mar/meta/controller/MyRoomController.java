package com.mar.meta.controller;

import com.mar.meta.entity.MyRoomEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mar.meta.service.MyRoomService;

@RestController
@RequestMapping(value = "/room")
public class MyRoomController {

	@Autowired
	MyRoomService myRoomService;
	
	@GetMapping("/expand/{id}")
	public int expandRoom(@PathVariable long id) {
		
		MyRoomEntity myRoomEntity = myRoomService.getMyRoomById(id);
		int level = myRoomEntity.getLevel();
		if(level < 4) {
			myRoomEntity.setLevel(level + 1);
			level = level + 1;
		}
		myRoomService.saveMyRoom(myRoomEntity);
		
		return level;
	}
}
