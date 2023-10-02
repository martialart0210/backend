package com.m2l.meta.repository;

import com.m2l.meta.entity.Shop;
import com.m2l.meta.enum_class.ShopType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop,Long> {
    Shop findAllByTypeAndCharacter_User_Username(ShopType type, String username);

}
