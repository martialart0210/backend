package com.m2l.meta.repository;

import com.m2l.meta.entity.DojoLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DojoLogRepo extends JpaRepository<DojoLog, Long> {
    List<DojoLog> findAllByOrderByCreatedAtDesc();
}
