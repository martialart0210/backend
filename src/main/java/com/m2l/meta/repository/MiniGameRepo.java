package com.m2l.meta.repository;

import com.m2l.meta.entity.MiniGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiniGameRepo extends JpaRepository<MiniGame,Long> {
}
