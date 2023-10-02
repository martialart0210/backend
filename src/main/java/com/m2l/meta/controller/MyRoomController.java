package com.m2l.meta.controller;

import com.m2l.meta.dto.ItemPlaceDto;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.utils.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.m2l.meta.service.MyRoomService;

@RestController
@RequestMapping(value = "/room")
public class MyRoomController extends BaseController {

    @Autowired
    MyRoomService myRoomService;

    @PostMapping("/expand")
    public ResponseEntity<?> expandRoom() {
        try {
            return success(CommonConstants.MessageSuccess.SC002, myRoomService.expandCurrentUserRoom(), null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> getRoomInfo() {
        try {
            return success(CommonConstants.MessageSuccess.SC002, myRoomService.expandCurrentUserRoom(), null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeOrRemoveItem(@RequestBody ItemPlaceDto itemPlaceDto) {
        try {
            return success(CommonConstants.MessageSuccess.SC002, myRoomService.placeOrReplaceItem(itemPlaceDto), null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), e.getParam());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllPlacedItemCurrentRoom() {
        try {
            return success(CommonConstants.MessageSuccess.SC001, myRoomService.getAllPlacedItemCurrentRoom(), null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @GetMapping("/item")
    public ResponseEntity<?> getBoughtItem() {
        try {
            return success(CommonConstants.MessageSuccess.SC001, myRoomService.getBoughtItem(), null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @PostMapping("/key")
    public ResponseEntity<?> generateKey() {
        try {
            return success(CommonConstants.MessageSuccess.SC001, myRoomService.generateRoomKey(), null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @GetMapping("/key")
    public ResponseEntity<?> getKey() {
        try {
            return success(CommonConstants.MessageSuccess.SC001, myRoomService.getRoomKey(), null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @PostMapping("/key-check")
    public ResponseEntity<?> generateKey(@RequestParam("characterId") Long characterId, @RequestParam("key") String key) {
        try {
            return success(CommonConstants.MessageSuccess.SC001, myRoomService.checkRoomKey(characterId, key), null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }
}
