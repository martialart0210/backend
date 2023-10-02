package com.m2l.meta.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "SHOP_ROOM_ITEM")
@Table(name = "shop_room_item")
public class ShopMyRoomItem {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID",referencedColumnName = "ITEM_ID")
    MyRoomItem item;

    @ManyToOne
    @JoinColumn(name = "SHOP_ID",referencedColumnName = "ID")
    Shop shop;

    @Column(name = "IS_SOLD_OUT")
    boolean isSoldOut;

}
