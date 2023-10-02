package com.m2l.meta.repository;

import com.m2l.meta.entity.RoomScrapBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomScrapBookRepository extends JpaRepository<RoomScrapBookEntity, Long> {

    List<RoomScrapBookEntity> findAllByUserCharacterCharacterId(Long characterId);
}
