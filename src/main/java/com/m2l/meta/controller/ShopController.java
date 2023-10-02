package com.m2l.meta.controller;

import com.m2l.meta.dto.ShopDto;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.service.ShopService;
import com.m2l.meta.utils.CommonConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/shop")
@Tag(name = "shop-api")
public class ShopController extends BaseController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/costume")
    public ResponseEntity<?> getCurrentUerCostumeShop() {
        try {
            ShopDto dto = shopService.getListCostume();
            return success(CommonConstants.MessageSuccess.SC001, dto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @GetMapping("/interior")
    public ResponseEntity<?> getCurrentUerInteriorShop() {
        try {
            ShopDto dto = shopService.getListInterior();
            return success(CommonConstants.MessageSuccess.SC001, dto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @PostMapping("/costume/buy/{itemId}")
    public ResponseEntity<?> costumePurchase(@PathVariable Long itemId) {
        try {
            shopService.costumePurchase(itemId);
            return success(CommonConstants.MessageSuccess.SC009, null, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @PostMapping("/interior/buy/{itemId}")
    public ResponseEntity<?> interiorPurchase(@PathVariable Long itemId) {
        try {
            shopService.interiorPurchase(itemId);
            return success(CommonConstants.MessageSuccess.SC009, null, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @DeleteMapping("/costume/sell/{itemId}")
    public ResponseEntity<?> sellingCostume(@PathVariable Long itemId) {
        try {
            shopService.sellingCostume(itemId);
            return success(CommonConstants.MessageSuccess.SC009, null, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @DeleteMapping("/interior/sell/{itemId}")
    public ResponseEntity<?> sellingInterior(@PathVariable Long itemId) {
        try {
            shopService.sellingInterior(itemId);
            return success(CommonConstants.MessageSuccess.SC009, null, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @PostMapping("/other/buy/coupon")
    public ResponseEntity<?> interiorPurchase() {
        try {
            shopService.buyCoupon();
            return success(CommonConstants.MessageSuccess.SC009, null, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }
}
