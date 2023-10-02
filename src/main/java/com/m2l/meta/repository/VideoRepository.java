package com.m2l.meta.repository;

import com.m2l.meta.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Page<Video> findAllByScrapBook_Id(Long idScrapbook, Pageable pageable);
}
