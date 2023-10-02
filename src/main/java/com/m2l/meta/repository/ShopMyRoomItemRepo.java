package com.m2l.meta.repository;

import com.m2l.meta.entity.ShopMyRoomItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopMyRoomItemRepo extends JpaRepository<ShopMyRoomItem,Long> {
    @Query(value = "SELECT COUNT(*) FROM shop_room_item WHERE ITEM_ID = :itemId AND SHOP_ID = :shopId AND IS_SOLD_OUT = 1", nativeQuery = true)
    Integer checkItemSoldOut(@Param("itemId") Long itemId, @Param("shopId") Long shopId);
    ShopMyRoomItem findAllByItem_IdAndShop_Id(Long itemId, Long shopId);
}
