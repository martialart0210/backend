package com.m2l.meta.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.m2l.meta.entity.UserRoleEntity;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {
    List<UserRoleEntity> findAllByUserId(Long userId);

    @Transactional
    @Modifying
    void deleteAllByUserId(Long userId);
}
