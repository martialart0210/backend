package com.m2l.meta.repository;

import com.m2l.meta.entity.ShopCostume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CostumeShopRepo extends JpaRepository<ShopCostume, Long> {
    @Query(value = "SELECT COUNT(*) FROM shop_costume WHERE COSTUME_ID = :costumeId AND SHOP_ID = :shopId AND IS_SOLD_OUT = 1", nativeQuery = true)
    Integer checkItemSoldOut(@Param("costumeId") Long costumeId, @Param("shopId") Long shopId);

    ShopCostume findAllByCostume_CostumeIdAndShop_Id(@Param("costumeId") Long costumeId, @Param("shopId") Long shopId);
}
