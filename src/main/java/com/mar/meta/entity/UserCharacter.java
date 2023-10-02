package com.mar.meta.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "UserCharacter")
@Table(name = "user_character")
public class UserCharacter {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToMany(mappedBy="character")
    List<CharacterQuestDetail> detailList;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User userId;

    @Column(name = "GOLD")
    private BigInteger gold;

}
