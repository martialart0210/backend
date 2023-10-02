package com.m2l.meta.entity;

import com.m2l.meta.enum_class.CostumeType;
import com.m2l.meta.enum_class.GenderEnum;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "COSTUME")
@Table(name = "costume")
public class CostumeEntity {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COSTUME_ID", nullable = false)
    private Long costumeId;

    @Column(name = "COSTUME_NAME", nullable = false)
    private String costumeName;

    @Column(name = "COSTUME_PRICE", nullable = false)
    private Integer costumePrice;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "COSTUME_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private CostumeType type = CostumeType.HAIR;

    @Column(name = "GENDER")
    @Enumerated(EnumType.ORDINAL)
    private GenderEnum gender = GenderEnum.MALE;

    @OneToMany(mappedBy="costume")
    @JsonIgnore
    List<ShopCostume> shopList;

    @OneToMany(mappedBy="item")
    @JsonIgnore
    List<WardrobeItem> wardrobeItemList;

}
