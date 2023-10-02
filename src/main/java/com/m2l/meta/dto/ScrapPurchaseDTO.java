package com.m2l.meta.dto;


import com.m2l.meta.entity.MyRoomEntity;
import com.m2l.meta.entity.UserCharacter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrapPurchaseDTO {

    private BigInteger  price;

    private UserCharacter userCharacter;

    private MyRoomEntity room;
}
