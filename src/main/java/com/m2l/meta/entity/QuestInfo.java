package com.m2l.meta.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "QUEST_INFO")
@Table(name = "quest_info")
public class QuestInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 3145254234350532564L;
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUEST_ID", nullable = false)
    private Long questInfoID;

    @Column(name = "REWARD", nullable = false)
    public int reward;

    @Column(name = "QUEST_DESCRIPTION", nullable = false)
    private String questDescription;

    @Column(name = "QUEST_NAME", nullable = false)
    public String questName;

    @OneToMany(mappedBy = "questInfo")
    List<CharacterQuestDetail> detailList;
}
