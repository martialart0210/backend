package com.m2l.meta.entity;

import com.m2l.meta.enum_class.DojoPosition;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "DOJO_MEMBER")
@Table(name = "dojo_member")
public class DojoMember {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "CHARACTER_ID",referencedColumnName = "CHARACTER_ID",nullable = false)
    private UserCharacter character;

    @ManyToOne
    @JoinColumn(name = "DOJO_ID",referencedColumnName = "ID", nullable = false)
    private Dojo dojo;

    @Enumerated(EnumType.ORDINAL)
    @JoinColumn(name = "POSITION", nullable = false)
    private DojoPosition position;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "CREATED_BY")
    private String createdBy;
}
