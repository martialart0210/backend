package com.m2l.meta.repository;

import com.m2l.meta.entity.DojoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DojoRequestRepo extends JpaRepository<DojoRequest,Long> {
    @Query(value = "select dr.*  from dojo_request dr join user_character uc on dr.CHARACTER_ID = uc.CHARACTER_ID " +
            "where dr.DOJO_ID = :dojoId and dr.REQUEST_STATUS = 0 order by dr.CREATED_AT desc ", nativeQuery = true,
            countQuery = "select dr.*  from dojo_request dr join user_character uc on dr.CHARACTER_ID = uc.CHARACTER_ID " +
                     "where dr.DOJO_ID = :dojoId and dr.REQUEST_STATUS = 0 order by dr.CREATED_AT desc ")
    Page<DojoRequest>findAllByDojo_Id(@Param("dojoId") Long dojoId, Pageable pageable);

    DojoRequest findAllByDojo_IdAndCharacter_CharacterId(Long dojoId, Long characterId);
}
