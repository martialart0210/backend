package com.m2l.meta.entity;

import com.m2l.meta.enum_class.ItemType;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "MY_ROOM_ITEM")
@Table(name = "my_room_item")
public class MyRoomItem {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID", nullable = false)
    private Long id;

    @Column(name = "ITEM_NAME", nullable = false)
    private String itemName;

    @Column(name = "ITEM_PRICE", nullable = false)
    private Integer itemPrice;

    @Column(name = "LENGTH")
    private int length;

    @Column(name = "WIDTH")
    private int width;

    @Column(name = "HEIGHT")
    private int height;

    @Column(name = "ITEM_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private ItemType type = ItemType.FURNITURE;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @OneToMany(mappedBy="item")
    @JsonIgnore
    private List<RoomDrawerEntity> roomDrawer;

    @OneToMany(mappedBy="item")
    @JsonIgnore
    List<ShopMyRoomItem> itemList;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final MyRoomItem item = (MyRoomItem) obj;
        return Objects.equals(this.id, item.id);
    }

    public MyRoomItem(Long id) {
        this.id = id;
    }
}
