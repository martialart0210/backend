package com.m2l.meta.repository;

import com.m2l.meta.entity.RoomDrawerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawnerRepository extends JpaRepository<RoomDrawerEntity, Long> {
    RoomDrawerEntity findAllByRoom_Character_CharacterIdAndItem_Id(Long characterId, Long itemId);
}
