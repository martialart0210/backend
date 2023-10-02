package com.mar.meta.repository;

import com.mar.meta.entity.UserCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<UserCharacter, Long> {
}
