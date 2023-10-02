package com.m2l.meta.service.impl;

import com.m2l.meta.dto.*;
import com.m2l.meta.entity.*;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.repository.*;
import com.m2l.meta.utils.CommonConstants;
import com.m2l.meta.utils.UserAuthUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.m2l.meta.service.MyRoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyRoomServiceImpl implements MyRoomService {

    @Autowired
    MyRoomRepository myRoomRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyRoomItemPalaceRepository myRoomItemPalaceRepository;

    @Autowired
    MyRoomItemRepository myRoomItemRepository;

    @Autowired
    UserAuthUtils authUtils;

    @Override
    public MyRoomEntity getMyRoomById(Long id) {
        return myRoomRepository.findById(id).orElse(new MyRoomEntity());
    }

    @Override
    public void saveMyRoom(MyRoomEntity myRoomEntity) {
        myRoomRepository.save(myRoomEntity);
    }

    @Override
    public Integer expandCurrentUserRoom() throws MamException {
        Optional<User> currentUserOptional = userRepository.findUsersByUsername(authUtils.getUserInfoFromReq().getUsername());
        if (currentUserOptional.isEmpty()) {
            throw new MamException("", null);
        }
        UserCharacter character = characterRepository.findAllByUser_Id(currentUserOptional.get().getId());
        MyRoomEntity myRoomEntity = myRoomRepository.findAllByCharacter_CharacterId(character.getCharacterId());
        if (myRoomEntity.getLevel() >= 3) {
            throw new MamException(CommonConstants.MessageError.ER037, null);
        }
//        if ((myRoomEntity.getLevel() + 1) > character.getExpansionCouponNumber()) {
//            throw new MamException(CommonConstants.MessageError.ER052, null);
//        }
        myRoomEntity.expand();
        myRoomRepository.save(myRoomEntity);
//        characterRepository.save(character);
        return myRoomEntity.getLevel();
    }

    @Override
    public ItemPlaceDto placeOrReplaceItem(ItemPlaceDto placeDto) throws MamException {
        UserDto dto = null;
        try {
            dto = authUtils.getUserInfo(authUtils.getUserInfoFromReq().getUsername());
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER027, null);
        }
        UserCharacter character = characterRepository.findAllByUser_Id(dto.getId());
        MyRoomEntity myRoom = myRoomRepository.findAllByCharacter_CharacterId(character.getCharacterId());
        if (myRoom.getDrawerEntity().stream().noneMatch(roomDrawerEntity -> roomDrawerEntity.getItem().equals(new MyRoomItem(placeDto.getItemId())))) {
            throw new MamException(CommonConstants.MessageError.ER047, new Object[]{placeDto.getItemId()});
        }
        MyRoomItem item = myRoom.getDrawerEntity().stream().filter(roomDrawerEntity -> roomDrawerEntity.getItem().equals(new MyRoomItem(placeDto.getItemId()))).toList().get(0).getItem();
//            Going to :
//            Validate position of item
//
//            place new item
        placeDto.setItemHeight((float) item.getHeight());
        placeDto.setItemLength((float) item.getLength());
        placeDto.setItemWidth((float) item.getWidth());
        if (ObjectUtils.isNotEmpty(placeDto.getAction()) && placeDto.getAction().equals(0)
                && (ObjectUtils.isEmpty(placeDto.getPlaceId()) || placeDto.getPlaceId() == 0)) {
            Optional<MyRoomItem> roomItem = myRoomItemRepository.findById(placeDto.getItemId());
            MyRoomItemPlace itemPlace = MyRoomItemPlace.builder()
                    .item(roomItem.orElseThrow())
                    .myRoom(myRoom)
                    .type(item.getType())
                    .posY(placeDto.getPosY())
                    .posX(placeDto.getPosX())
                    .posZ(placeDto.getPosZ())
                    .isFloorItem(placeDto.getPosition().equals(0))
                    .rotY(placeDto.getRotY())
                    .rotZ(placeDto.getRotZ())
                    .rotX(placeDto.getRotX())
                    .rotateNumber(placeDto.getRotateNumber())
                    .build();
            itemPlace = myRoomItemPalaceRepository.save(itemPlace);
            placeDto.setPlaceId(itemPlace.getId());
            placeDto.setRotateNumber(itemPlace.getRotateNumber());
        }
//            remove item
        if (ObjectUtils.isNotEmpty(placeDto.getAction()) && ObjectUtils.isNotEmpty(placeDto.getPlaceId())) {
            if (placeDto.getAction().equals(1)) {
                myRoomItemPalaceRepository.deleteById(placeDto.getPlaceId());
            }
            if (placeDto.getAction().equals(0)) {
                MyRoomItemPlace itemPlace;
                Optional<MyRoomItemPlace> optional = myRoomItemPalaceRepository.findById(placeDto.getPlaceId());
                if (optional.isEmpty()) {
                    throw new MamException(CommonConstants.MessageError.ER017, null);
                }
                itemPlace = optional.get();
                itemPlace.setPosZ(placeDto.getPosZ());
                itemPlace.setPosY(placeDto.getPosY());
                itemPlace.setPosX(placeDto.getPosX());
                itemPlace.setRotX(placeDto.getRotX());
                itemPlace.setRotY(placeDto.getRotY());
                itemPlace.setRotZ(placeDto.getRotZ());
                itemPlace.setRotateNumber(placeDto.getRotateNumber());
                itemPlace = myRoomItemPalaceRepository.save(itemPlace);
                placeDto.setRotateNumber(itemPlace.getRotateNumber());
            }
        }
        placeDto.setAction(null);
        return placeDto;
    }

    @Override
    public List<ItemPlaceDto> getAllPlacedItemCurrentRoom() throws MamException {
        try {
            UserDto dto = authUtils.getUserInfo(authUtils.getUserInfoFromReq().getUsername());
            UserCharacter character = characterRepository.findAllByUser_Id(dto.getId());
            MyRoomEntity myRoom = myRoomRepository.findAllByCharacter_CharacterId(character.getCharacterId());
            return myRoomItemPalaceRepository.findAllByMyRoom_Id(myRoom.getId()).stream().map(myRoomItemPlace ->
                    ItemPlaceDto.builder()
                            .placeId(myRoomItemPlace.getId())
                            .action(null)
                            .itemId(myRoomItemPlace.getItem().getId())
                            .itemName(myRoomItemPlace.getItem().getItemName())
                            .position(myRoomItemPlace.getIsFloorItem() ? 0 : 1)
                            .itemType(myRoomItemPlace.getType().getValue())
                            .posZ(myRoomItemPlace.getPosZ())
                            .posY(myRoomItemPlace.getPosY())
                            .posX(myRoomItemPlace.getPosX())
                            .rotY(myRoomItemPlace.getRotY())
                            .rotZ(myRoomItemPlace.getRotZ())
                            .rotX(myRoomItemPlace.getRotX())
                            .itemHeight((float) myRoomItemPlace.getItem().getHeight())
                            .itemLength((float) myRoomItemPlace.getItem().getLength())
                            .itemWidth((float) myRoomItemPlace.getItem().getWidth())
                            .rotateNumber(myRoomItemPlace.getRotateNumber())
                            .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER033, null);
        }
    }

    @Override
    public ItemBoughtDto getBoughtItem() throws MamException {
        try {
            UserDto dto = authUtils.getUserInfo(authUtils.getUserInfoFromReq().getUsername());
            UserCharacter character = characterRepository.findAllByUser_Id(dto.getId());
            MyRoomEntity myRoom = myRoomRepository.findAllByCharacter_CharacterId(character.getCharacterId());
            return ItemBoughtDto.builder()
                    .drawerItems(myRoom.getDrawerEntity().stream().map(roomDrawerEntity -> ObjectUtils.isEmpty(roomDrawerEntity.getItem()) ? null : DrawerItemDto.builder()
                            .itemId(roomDrawerEntity.getItem().getId())
                            .itemPrice(roomDrawerEntity.getItem().getItemPrice())
                            .description(roomDrawerEntity.getItem().getDescription())
                            .width(roomDrawerEntity.getItem().getWidth())
                            .height(roomDrawerEntity.getItem().getHeight())
                            .length(roomDrawerEntity.getItem().getLength())
                            .type(roomDrawerEntity.getItem().getType().name())
                            .itemName(roomDrawerEntity.getItem().getItemName())
                            .build()).toList())
                    .wardrobeItems(myRoom.getItemList().stream().map(wardrobeItem -> ObjectUtils.isEmpty(wardrobeItem.getItem()) ? null : WardrobeItemDto.builder()
                            .itemId(wardrobeItem.getItem().getCostumeId())
                            .price(wardrobeItem.getItem().getCostumePrice())
                            .itemName(wardrobeItem.getItem().getCostumeName())
                            .itemId(wardrobeItem.getItem().getCostumeId())
                            .itemDescription(wardrobeItem.getItem().getDescription())
                            .isEquiped(wardrobeItem.isEquipped())
                            .gender(wardrobeItem.getItem().getGender().name())
                            .itemType(wardrobeItem.getItem().getType().name())
                            .build()).toList())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MamException(CommonConstants.MessageError.ER033, null);
        }
    }

    @Override
    public String getRoomKey() throws MamException {
        UserDto dto = null;
        try {
            dto = authUtils.getUserInfo(authUtils.getUserInfoFromReq().getUsername());
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER033, null);
        }
        UserCharacter character = characterRepository.findAllByUser_Id(dto.getId());
        MyRoomEntity myRoom = myRoomRepository.findAllByCharacter_CharacterId(character.getCharacterId());
        return myRoom.getRoomKey();
    }

    @Override
    public String generateRoomKey() throws MamException {
        UserDto dto;
        try {
            dto = authUtils.getUserInfo(authUtils.getUserInfoFromReq().getUsername());
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER033, null);
        }
        UserCharacter character = characterRepository.findAllByUser_Id(dto.getId());
        MyRoomEntity myRoom = myRoomRepository.findAllByCharacter_CharacterId(character.getCharacterId());
        myRoom.setRoomKey(myRoom.generateKey());
        myRoomRepository.save(myRoom);
        return myRoom.getRoomKey();
    }

    @Override
    public Boolean checkRoomKey(Long characterId, String roomKey) throws MamException {
        Optional<UserCharacter> character = characterRepository.findById(characterId);
        if (character.isEmpty()) {
            throw new MamException(CommonConstants.MessageError.ER051, null);
        }
        MyRoomEntity myRoom = myRoomRepository.findAllByCharacter_CharacterId(character.get().getCharacterId());
        return myRoom.getRoomKey().equals(roomKey.toUpperCase());
    }



}
