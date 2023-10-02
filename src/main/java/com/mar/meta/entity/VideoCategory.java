package com.mar.meta.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "VIDEO_CATEGORY")
@Table(name = "video_category")
public class VideoCategory {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "VIDEO_CATEGORY_ID", nullable = false)
    private Long id;

    @Column(name = "CATEGORY_NAME", nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "videoCategory")
    List<Video> videoList;

}
