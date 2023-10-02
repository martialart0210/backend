package com.mar.meta.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * The Class Video.
 *
 * @author <a href="mailto:an@dxplus.io">an</a>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "VIDEO")
@Table(name = "videos")
public class Video {
    private static final long serialVersionUID = 7629287212050058050L;
    @Id
    @Column(name = "VIDEO_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "VIDEO_LINK", length = 1000)
    private String videoLink;
    @ManyToOne
    @JoinColumn(name="VIDEO_CATEGORY_ID", nullable=false)
    VideoCategory videoCategory;

}
