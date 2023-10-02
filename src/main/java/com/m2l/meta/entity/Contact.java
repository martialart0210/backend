package com.m2l.meta.entity;

import com.m2l.meta.enum_class.ContactType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "CONTACT")
@Table(name = "contact")
public class Contact {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CHARACTER_ID", nullable = false)
    private UserCharacter characterId;

    @ManyToOne
    @JoinColumn(name = "CONTACT_ID", nullable = false)
    private UserCharacter contactId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "CONTACT_TYPE", nullable = false)
    private ContactType type;

}
