package com.mar.meta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mar.meta.entity.RoleEntity;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    List<RoleEntity> findAll();

    @Query(value = "select r.* from roles r join user_role ur WHERE ur.USER_ID = :userId", nativeQuery = true)
    List<RoleEntity> getRolesUser(@Param("userId") Long userId);

    RoleEntity findByRoleName(String name);
}
