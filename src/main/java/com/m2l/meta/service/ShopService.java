package com.m2l.meta.service;

import com.m2l.meta.dto.ShopDto;
import com.m2l.meta.exceptions.MamException;

public interface ShopService {
    ShopDto getListCostume() throws MamException;
    ShopDto getListInterior() throws MamException;

    void costumePurchase(Long costumeId) throws MamException;

    void interiorPurchase(Long itemId)throws MamException;

    void buyCoupon() throws MamException;

    void sellingCostume(Long costumeId) throws MamException;

    void sellingInterior(Long interiorId) throws MamException;
}
