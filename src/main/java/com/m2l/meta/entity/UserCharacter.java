package com.m2l.meta.entity;

import com.m2l.meta.enum_class.GenderEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "USER_CHARACTER")
@Table(name = "user_character")
public class UserCharacter {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHARACTER_ID", nullable = false)
    private Long characterId;

    @OneToMany(mappedBy = "character")
    List<CharacterQuestDetail> detailList;

    @OneToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, orphanRemoval = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
    private MyRoomEntity room;

    @Column(name = "GOLD")
    private BigInteger gold;

    @Column(name = "CHARACTER_NAME")
    private String characterName;

    @Column(name = "GENDER")
    @Enumerated(EnumType.ORDINAL)
    private GenderEnum gender = GenderEnum.MALE;

    @Column(name = "CHARACTER_MODEL")
    private String characterModel;

    @Column(name = "EXPANSION_COUPON_NUMBER")
    private Integer expansionCouponNumber = 0;

    @Column(name = "SCRAPBOOK_NUMBER")
    private Integer scrapbookNumber = 0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COSTUME_HAIR", referencedColumnName = "COSTUME_ID")
    private CostumeEntity costumeHair;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COSTUME_TOP", referencedColumnName = "COSTUME_ID")
    private CostumeEntity costumeTop;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COSTUME_SHOE", referencedColumnName = "COSTUME_ID")
    private CostumeEntity costumeShoe;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COSTUME_BOTTOM", referencedColumnName = "COSTUME_ID")
    private CostumeEntity costumeBottom;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COSTUME_ACCESSORY", referencedColumnName = "COSTUME_ID")
    private CostumeEntity costumeAccessory;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "character", orphanRemoval = true)
    private List<MiniGameRecord> gameRecords;

    @OneToMany(mappedBy = "characterId", orphanRemoval = true)
    private List<Contact> contactList;

    @OneToMany(mappedBy = "contactId", orphanRemoval = true)
    private List<Contact> inContacts;

    @OneToMany(mappedBy = "character", orphanRemoval = true)
    List<DojoRequest> dojoRequestList;

    @OneToOne(mappedBy = "character", orphanRemoval = true)
    DojoMember dojoMember;

    @OneToMany(mappedBy = "character", orphanRemoval = true)
    List<CharacterQuestDetail> questDetailList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "character", orphanRemoval = true)
    private List<Shop> shop;

    @OneToMany(mappedBy="userCharacter", orphanRemoval = true)
    private List<RoomScrapBookEntity> roomScrapBookEntityList;

    @Column(name = "DOJO_OUT_TIME")
    private LocalDateTime dojoOutTime;

    @Column(name = "ABOUT_ME")
    private String aboutMe;

}
