package com.m2l.meta.repository;

import com.m2l.meta.entity.MyRoomItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyRoomItemRepository extends JpaRepository<MyRoomItem, Long> {
}
