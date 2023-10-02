package com.m2l.meta.repository;

import com.m2l.meta.entity.MyRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRoomRepository extends JpaRepository<MyRoomEntity,Long> {

    MyRoomEntity findAllByCharacter_CharacterId(Long characterId);
    MyRoomEntity findAllByCharacter_User_Username(String username);

    MyRoomEntity findByCharacterCharacterId(Long characterid);

}
