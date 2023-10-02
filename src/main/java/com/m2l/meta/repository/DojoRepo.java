package com.m2l.meta.repository;

import com.m2l.meta.entity.Dojo;
import com.m2l.meta.enum_class.DojoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DojoRepo extends JpaRepository<Dojo,Long> {
    @Query(value = " SELECT * FROM dojo AS t1 JOIN (SELECT id FROM dojo WHERE (dojo.DOJO_NAME LIKE CONCAT('%',:dojoName, '%') OR LENGTH(:dojoName) = 0) AND DOJO_STATUS = 0 ORDER BY RAND() LIMIT 10) as t2 ON t1.id=t2.id ", nativeQuery = true)
    List<Dojo> findDojoRandom(@Param("dojoName") String dojoName);

    Boolean existsByDojoName(String dojoName);
}
