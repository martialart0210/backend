package com.m2l.meta.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "SHOP_COSTUME")
@Table(name = "shop_costume")
public class ShopCostume {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COSTUME_ID",referencedColumnName = "COSTUME_ID")
    CostumeEntity costume;

    @ManyToOne
    @JoinColumn(name = "SHOP_ID",referencedColumnName = "ID")
    Shop shop;

    @Column(name = "IS_SOLD_OUT")
    boolean isSoldOut;

}
