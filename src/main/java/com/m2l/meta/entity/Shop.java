package com.m2l.meta.entity;

import com.m2l.meta.enum_class.ShopType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "SHOP")
@Table(name = "shop")
public class Shop implements Serializable {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CHARACTER_ID", referencedColumnName = "CHARACTER_ID")
    private UserCharacter character;

    @Column(name = "SHOP_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private ShopType type = ShopType.COSTUME_SHOP;

    @OneToMany(mappedBy="shop", orphanRemoval = true)
    List<ShopCostume> costumeList;

    @OneToMany(mappedBy="shop", orphanRemoval = true)
    List<ShopMyRoomItem> itemList;

    public Shop(UserCharacter character) {
        this.character = character;
    }
}
