package com.m2l.meta.service.impl;

import com.m2l.meta.dto.ItemDto;
import com.m2l.meta.dto.ShopDto;
import com.m2l.meta.entity.*;
import com.m2l.meta.enum_class.ItemType;
import com.m2l.meta.enum_class.ShopType;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.repository.*;
import com.m2l.meta.service.ShopService;
import com.m2l.meta.utils.CommonConstants;
import com.m2l.meta.utils.UserAuthUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Objects;

@Service
public class ShopServiceImpl implements ShopService {
    private CostumeRepository costumeRepository;
    private MyRoomItemRepository roomItemRepository;
    private ShopRepository shopRepository;

    private MyRoomRepository myRoomRepository;

    private CharacterRepository characterRepository;

    private CostumeShopRepo costumeShopRepo;

    private WardrobeItemRepo wardrobeItemRepo;

    private ShopMyRoomItemRepo shopMyRoomItemRepo;

    private DrawnerRepository drawnerRepository;

    @Autowired
    private UserAuthUtils authUtils;

    @Autowired
    public ShopServiceImpl(CostumeRepository costumeRepository, MyRoomItemRepository roomItemRepository,
                           ShopRepository shopRepository, MyRoomRepository myRoomRepository, CharacterRepository characterRepository,
                           CostumeShopRepo costumeShopRepo, WardrobeItemRepo wardrobeItemRepo, ShopMyRoomItemRepo shopMyRoomItemRepo,
                           DrawnerRepository drawnerRepository) {
        this.costumeRepository = costumeRepository;
        this.roomItemRepository = roomItemRepository;
        this.shopRepository = shopRepository;
        this.myRoomRepository = myRoomRepository;
        this.characterRepository = characterRepository;
        this.costumeShopRepo = costumeShopRepo;
        this.wardrobeItemRepo = wardrobeItemRepo;
        this.shopMyRoomItemRepo = shopMyRoomItemRepo;
        this.drawnerRepository = drawnerRepository;
    }

    @Override
    public ShopDto getListCostume() throws MamException {
        Shop shop = shopRepository.findAllByTypeAndCharacter_User_Username(ShopType.COSTUME_SHOP, authUtils.getUserInfoFromReq().getUsername());
        return ShopDto.builder()
                .shopType(ShopType.COSTUME_SHOP.getValue())
                .listItem(shop.getCostumeList().stream().map(shopCostume -> ItemDto.builder()
                        .gender(shopCostume.getCostume().getGender().getValue())
                        .itemDescription(shopCostume.getCostume().getDescription())
                        .price(shopCostume.getCostume().getCostumePrice())
                        .itemId(shopCostume.getCostume().getCostumeId())
                        .itemName(shopCostume.getCostume().getCostumeName())
                        .isSoldOut(shopCostume.isSoldOut())
                        .itemType(shopCostume.getCostume().getType().getValue())
                        .build()).toList())
                .build();
    }

    @Override
    public ShopDto getListInterior() throws MamException {
        Shop shop = shopRepository.findAllByTypeAndCharacter_User_Username(ShopType.INTERIOR_SHOP, authUtils.getUserInfoFromReq().getUsername());
        return ShopDto.builder()
                .shopType(ShopType.INTERIOR_SHOP.getValue())
                .listItem(shop.getItemList().stream().map(listInterior -> ItemDto.builder()
                        .itemDescription(listInterior.getItem().getDescription())
                        .price(listInterior.getItem().getItemPrice())
                        .itemId(listInterior.getItem().getId())
                        .itemName(listInterior.getItem().getItemName())
                        .isSoldOut(listInterior.isSoldOut())
                        .itemType(listInterior.getItem().getType().getValue())
                        .length(listInterior.getItem().getLength())
                        .width(listInterior.getItem().getWidth())
                        .height(listInterior.getItem().getHeight())
                        .build()).toList())
                .build();
    }

    @Override
    public void costumePurchase(Long costumeId) throws MamException {
        Shop shop = shopRepository.findAllByTypeAndCharacter_User_Username(ShopType.COSTUME_SHOP, authUtils.getUserInfoFromReq().getUsername());
        CostumeEntity costume = costumeRepository.findById(costumeId).orElse(new CostumeEntity());
        if (costumeShopRepo.checkItemSoldOut(costumeId, shop.getId()) > 0) {
            throw new MamException("ER038", null);
        }
        if (!Objects.equals(costume.getGender().getValue(), shop.getCharacter().getGender().getValue())) {
            throw new MamException("ER039", null);
        }
        if (costume.getCostumePrice() > shop.getCharacter().getGold().intValue()) {
            throw new MamException("ER040", null);
        }
        MyRoomEntity roomEntity = myRoomRepository.findAllByCharacter_User_Username(authUtils.getUserInfoFromReq().getUsername());
        ShopCostume shopCostume = costumeShopRepo.findAllByCostume_CostumeIdAndShop_Id(costumeId, shop.getId());
        shopCostume.setSoldOut(true);
        costumeShopRepo.save(shopCostume);

        UserCharacter character = shop.getCharacter();
        character.setGold(character.getGold().subtract(BigInteger.valueOf(shopCostume.getCostume().getCostumePrice())));
        characterRepository.save(character);

        WardrobeItem item = WardrobeItem.builder()
                .item(shopCostume.getCostume())
                .room(roomEntity)
                .build();
        wardrobeItemRepo.save(item);
    }

    @Override
    public void interiorPurchase(Long itemId) throws MamException {
        Shop shop = shopRepository.findAllByTypeAndCharacter_User_Username(ShopType.INTERIOR_SHOP, authUtils.getUserInfoFromReq().getUsername());
        MyRoomItem roomItem = roomItemRepository.findById(itemId).orElse(new MyRoomItem());
        UserCharacter character = shop.getCharacter();
        if (shopMyRoomItemRepo.checkItemSoldOut(itemId, shop.getId()) > 0) {
            throw new MamException("ER038", null);
        }
        if (roomItem.getItemPrice() > character.getGold().intValue()) {
            throw new MamException("ER040", null);
        }
        ShopMyRoomItem shopMyRoomItem = shopMyRoomItemRepo.findAllByItem_IdAndShop_Id(itemId, shop.getId());
        if (shopMyRoomItem.getItem().getType().compareTo(ItemType.OTHER) != 0) {
            shopMyRoomItem.setSoldOut(true);
        }
        shopMyRoomItemRepo.save(shopMyRoomItem);

        character.setGold(character.getGold().subtract(BigInteger.valueOf(roomItem.getItemPrice())));
        characterRepository.save(character);

        RoomDrawerEntity roomDrawer = RoomDrawerEntity.builder()
                .room(character.getRoom())
                .item(roomItem)
                .build();
        ;
        drawnerRepository.save(roomDrawer);
    }

    @Override
    public void buyCoupon() throws MamException {
        Shop shop = shopRepository.findAllByTypeAndCharacter_User_Username(ShopType.INTERIOR_SHOP, authUtils.getUserInfoFromReq().getUsername());
        UserCharacter character = shop.getCharacter();
        if ((character.getExpansionCouponNumber() + 1) > 4) {
            throw new MamException(CommonConstants.MessageError.ER054, null);
        }
        if (character.getGold().compareTo(BigInteger.valueOf(300)) < 0) {
            throw new MamException(CommonConstants.MessageError.ER040, null);
        }
        character.setExpansionCouponNumber(character.getExpansionCouponNumber() + 1);
        character.setGold(character.getGold().subtract(BigInteger.valueOf(300)));
        characterRepository.save(character);
    }

    @Override
    public void sellingCostume(Long costumeId) throws MamException {
        Shop shop = shopRepository.findAllByTypeAndCharacter_User_Username(ShopType.COSTUME_SHOP, authUtils.getUserInfoFromReq().getUsername());
        UserCharacter character = shop.getCharacter();
        WardrobeItem costume = wardrobeItemRepo.findAllByItem_CostumeIdAndRoom_Id(costumeId, character.getRoom().getId());
        if (ObjectUtils.isEmpty(costume)) {
            throw new MamException(CommonConstants.MessageError.ER042, new Object[]{costumeId});
        }
        CostumeEntity costumeEntity = costume.getItem();
        ShopCostume shopCostume = costumeShopRepo.findAllByCostume_CostumeIdAndShop_Id(costumeEntity.getCostumeId(), shop.getId());
        shopCostume.setSoldOut(false);
        BigInteger gold = character.getGold().add(BigInteger.valueOf(costume.getItem().getCostumePrice() * 90 / 100));
        character.setGold(gold);
        characterRepository.save(character);
        costumeShopRepo.save(shopCostume);
        wardrobeItemRepo.delete(costume);
    }

    @Override
    public void sellingInterior(Long interiorId) throws MamException {
        Shop shop = shopRepository.findAllByTypeAndCharacter_User_Username(ShopType.INTERIOR_SHOP, authUtils.getUserInfoFromReq().getUsername());
        UserCharacter character = shop.getCharacter();
        RoomDrawerEntity interiorItem = drawnerRepository.findAllByRoom_Character_CharacterIdAndItem_Id(character.getCharacterId(), interiorId);
        if (ObjectUtils.isEmpty(interiorItem)) {
            throw new MamException(CommonConstants.MessageError.ER042, new Object[]{interiorId});
        }
        ShopMyRoomItem shopMyRoomItem = shopMyRoomItemRepo.findAllByItem_IdAndShop_Id(interiorId, shop.getId());
        shopMyRoomItem.setSoldOut(false);
        BigInteger gold = character.getGold().add(BigInteger.valueOf(interiorItem.getItem().getItemPrice() * 90 / 100));
        character.setGold(gold);
        characterRepository.save(character);
        shopMyRoomItemRepo.save(shopMyRoomItem);
        drawnerRepository.delete(interiorItem);
    }
}
