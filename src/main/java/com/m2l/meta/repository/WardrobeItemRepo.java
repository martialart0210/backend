package com.m2l.meta.repository;

import com.m2l.meta.entity.WardrobeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WardrobeItemRepo extends JpaRepository<WardrobeItem, Long> {
    WardrobeItem findAllByItem_CostumeIdAndRoom_Id(Long itemId, Long roomId);
}
