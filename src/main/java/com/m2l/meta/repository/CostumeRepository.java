package com.m2l.meta.repository;

import com.m2l.meta.entity.CostumeEntity;
import com.m2l.meta.enum_class.CostumeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CostumeRepository extends JpaRepository<CostumeEntity, Long> {

    Optional<CostumeEntity> findAllByCostumeIdAndAndType(Long costumeId, CostumeType type);

}
