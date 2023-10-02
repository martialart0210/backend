package com.m2l.meta.repository;

import com.m2l.meta.entity.MyRoomItemPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyRoomItemPalaceRepository extends JpaRepository<MyRoomItemPlace, Long> {
    List<MyRoomItemPlace> findAllByMyRoom_Id(Long roomId);
}
