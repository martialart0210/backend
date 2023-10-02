package com.mar.meta.repository;

import com.mar.meta.entity.VideoCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<VideoCategory, Long> {
}
