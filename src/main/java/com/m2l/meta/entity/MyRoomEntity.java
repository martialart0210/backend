package com.m2l.meta.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "MY_ROOM")
@Table(name = "my_room")
public class MyRoomEntity {

    @Id
    @Column(name = "ROOM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LEVEL")
    private int level;

    @Column(name = "LENGTH")
    private int length = 6;

    @Column(name = "WIDTH")
    private int width = 5;

    @Column(name = "HEIGHT")
    private int height = 3;

    @Column(name = "ROOM_KEY")
    private String roomKey = generateKey();

    @OneToOne(mappedBy = "room")
    private UserCharacter character;

    @OneToMany(mappedBy = "room", orphanRemoval = true)
    private List<RoomDrawerEntity> drawerEntity;

    @OneToMany(mappedBy = "room", orphanRemoval = true)
    private List<WardrobeItem> itemList;

    @OneToMany(mappedBy = "room", orphanRemoval = true)
    private List<RoomScrapBookEntity> scrapBookEntity;

    @ManyToOne
    @JoinColumn(name = "WALLPAPER_ID")
    private MyRoomItem wallpaper;

    @ManyToOne
    @JoinColumn(name = "FLOORING_ID")
    private MyRoomItem flooring;

    @OneToMany(mappedBy = "myRoom", orphanRemoval = true)
    private List<MyRoomItemPlace> itemPlaceList;

    public void expand() {
        this.level++;
        this.length++;
        this.width++;
    }

    public String generateKey() {
        return RandomStringUtils.randomAlphanumeric(8).toUpperCase();
    }


}
