//package com.mar.meta.controller;
//
//import com.mar.meta.dto.RoomDto;
//import com.mar.meta.exceptions.MamException;
//import com.mar.meta.service.RoomService;
//import com.mar.meta.utils.CommonConstants;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value = "/room")
//@Tag(name = "room-api")
//public class RoomController extends BaseController {
//
//    private RoomService roomService;
//
//    @Autowired
//    public RoomController(RoomService roomService) {
//        this.roomService = roomService;
//    }
//
//    @Operation(summary = "Expand room of current user")
//    @PutMapping(value = "/up/{level}")
//    public ResponseEntity<?> expandRoom(@PathVariable("level") Integer level) throws MamException {
//        RoomDto roomDto = roomService.expandRoom(level);
//        return success(CommonConstants.MessageSuccess.SC003, roomDto, null);
//    }
//}
