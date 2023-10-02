package com.m2l.meta.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "MINI_GAME_RECORD")
@Table(name = "mini_game_record")
public class MiniGameRecord {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "DAILY_RECORD")
    private Integer dailyRecord;

    @Column(name = "BEST_RECORD")
    private Integer bestRecord;

    @Column(name = "IS_CLAIMED")
    private Boolean isClaimed;

    @Column(name = "BEST_AT")
    private LocalDateTime bestAt;

    @ManyToOne
    @JoinColumn(name = "CHARACTER_ID", referencedColumnName = "CHARACTER_ID")
    private UserCharacter character;

    @ManyToOne
    @JoinColumn(name = "GAME_ID", referencedColumnName = "ID")
    private MiniGame miniGame;
}
