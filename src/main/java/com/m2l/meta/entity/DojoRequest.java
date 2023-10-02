package com.m2l.meta.entity;

import com.m2l.meta.enum_class.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "DOJO_REQUEST")
@Table(name = "dojo_request")
public class DojoRequest {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CHARACTER_ID", nullable = false)
    private UserCharacter character;

    @ManyToOne
    @JoinColumn(name = "DOJO_ID", nullable = false)
    private Dojo dojo;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "REQUEST_STATUS")
    RequestStatus requestStatus;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;


}
