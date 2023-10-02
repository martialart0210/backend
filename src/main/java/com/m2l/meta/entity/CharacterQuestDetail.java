package com.m2l.meta.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "CHARACTER_QUEST_DETAIL")
@Table(name = "character_quest_detail")
public class CharacterQuestDetail {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "PERFORMED_COUNT", nullable = false)
    private int performedCount;

    @Column(name = "MAX_PERFORMED", nullable = false)
    private int maxPerformed;

    @Column(name = "IS_ACCEPT", nullable = false)
    private boolean isAccept;

    @Column(name = "IS_COMPLETED", nullable = false)
    private boolean isCompleted;

    @Column(name = "IS_FINISHED", nullable = false)
    private boolean isFinished;

    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "QUEST_ID", nullable = false)
    private QuestInfo questInfo;
  
    @ManyToOne
    @JoinColumn(name = "CHARACTER_ID", nullable = false)
    private UserCharacter character;
}
