package com.m2l.meta.repository;

import com.m2l.meta.entity.UserCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<UserCharacter, Long> {

    UserCharacter findAllByUser_Id(Long userId);

    Integer countUserCharacterByCharacterName(String name);
}
