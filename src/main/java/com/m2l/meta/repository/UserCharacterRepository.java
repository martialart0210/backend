package com.m2l.meta.repository;

import com.m2l.meta.entity.UserCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCharacterRepository extends JpaRepository<UserCharacter, Long> {

    UserCharacter findByUserId(Long userId);

}
