package com.m2l.meta.repository;

import com.m2l.meta.dto.DojoMemberDto;
import com.m2l.meta.dto.DojoMemberInf;
import com.m2l.meta.entity.DojoMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DojoMemberRepo extends JpaRepository<DojoMember,Long> {
    @Query(value = "SELECT  dm.ID as memberId, dm.CHARACTER_ID as characterId, uc.CHARACTER_NAME as characterName,   " +
            "            CASE WHEN u.CONNECTION_STATUS = 1 THEN 'Now Connecting'   " +
            "            ELSE    " +
            "            CASE WHEN TIMESTAMPDIFF(DAY,u.LAST_ACCESS,NOW()) > 0 THEN CONcat(TIMESTAMPDIFF(DAY,u.LAST_ACCESS,NOW()),' days ago')   " +
            "             WHEN TIMESTAMPDIFF(DAY,u.LAST_ACCESS,NOW()) = 0 AND TIMESTAMPDIFF(HOUR,u.LAST_ACCESS,NOW()) > 0 THEN  CONCAT(TIMESTAMPDIFF(HOUR,u.LAST_ACCESS,NOW()), ' hours ago')   " +
            "             WHEN TIMESTAMPDIFF(DAY,u.LAST_ACCESS,NOW()) = 0 AND TIMESTAMPDIFF(HOUR,u.LAST_ACCESS,NOW()) = 0 AND TIMESTAMPDIFF(MINUTE,u.LAST_ACCESS,NOW()) > 0 THEN  CONCAT(TIMESTAMPDIFF(MINUTE,u.LAST_ACCESS,NOW()), ' minutes ago')   " +
            "             WHEN TIMESTAMPDIFF(DAY,u.LAST_ACCESS,NOW()) = 0 AND TIMESTAMPDIFF(HOUR,u.LAST_ACCESS,NOW()) = 0 AND TIMESTAMPDIFF(MINUTE,u.LAST_ACCESS,NOW()) = 0 THEN CONCAT(TIMESTAMPDIFF(SECOND,u.LAST_ACCESS,NOW()), ' seconds ago')   " +
            "            ELSE NULL    " +
            "            END   " +
            "            END as lastAccessTime, " +
            "            CASE WHEN uc.GENDER = 0 THEN 'MALE' ELSE 'FEMALE' END as characterGender , " +
            "            CASE dm.position WHEN 0 THEN 'INSTRUCTOR'  " +
            "            WHEN 1 THEN 'SUB_INSTRUCTOR' " +
            "            ELSE 'TRAINEE' " +
            "            END as memberPosition " +
            "            FROM dojo_member dm    " +
            "            JOIN user_character uc ON dm.CHARACTER_ID = uc.CHARACTER_ID    " +
            "            JOIN users u ON uc.USER_ID = u.ID   " +
            "            WHERE dm.DOJO_ID = :dojoId   " +
            "            ORDER BY  dm.CREATED_AT DESC ;", nativeQuery = true,
            countQuery = "SELECT  dm.ID as memberId, dm.CHARACTER_ID as characterId, uc.CHARACTER_NAME as characterName,   " +
                    "            CASE WHEN u.CONNECTION_STATUS = 1 THEN 'Now Connecting'   " +
                    "            ELSE    " +
                    "            CASE WHEN TIMESTAMPDIFF(DAY,u.LAST_ACCESS,NOW()) > 0 THEN CONcat(TIMESTAMPDIFF(DAY,u.LAST_ACCESS,NOW()),' days ago')   " +
                    "             WHEN TIMESTAMPDIFF(DAY,u.LAST_ACCESS,NOW()) = 0 AND TIMESTAMPDIFF(HOUR,u.LAST_ACCESS,NOW()) > 0 THEN  CONCAT(TIMESTAMPDIFF(HOUR,u.LAST_ACCESS,NOW()), ' hours ago')   " +
                    "             WHEN TIMESTAMPDIFF(DAY,u.LAST_ACCESS,NOW()) = 0 AND TIMESTAMPDIFF(HOUR,u.LAST_ACCESS,NOW()) = 0 AND TIMESTAMPDIFF(MINUTE,u.LAST_ACCESS,NOW()) > 0 THEN  CONCAT(TIMESTAMPDIFF(MINUTE,u.LAST_ACCESS,NOW()), ' minutes ago')   " +
                    "             WHEN TIMESTAMPDIFF(DAY,u.LAST_ACCESS,NOW()) = 0 AND TIMESTAMPDIFF(HOUR,u.LAST_ACCESS,NOW()) = 0 AND TIMESTAMPDIFF(MINUTE,u.LAST_ACCESS,NOW()) = 0 THEN CONCAT(TIMESTAMPDIFF(SECOND,u.LAST_ACCESS,NOW()), ' seconds ago')   " +
                    "            ELSE NULL    " +
                    "            END   " +
                    "            END as lastAcessTime, " +
                    "            CASE WHEN uc.GENDER = 0 THEN 'MALE' ELSE 'FEMALE' END as characterGender , " +
                    "            CASE dm.position WHEN 0 THEN 'INSTRUCTOR'  " +
                    "            WHEN 1 THEN 'SUB_INSTRUCTOR' " +
                    "            ELSE 'TRAINEE' " +
                    "            END as memberPosition " +
                    "            FROM dojo_member dm    " +
                    "            JOIN user_character uc ON dm.CHARACTER_ID = uc.CHARACTER_ID    " +
                    "            JOIN users u ON uc.USER_ID = u.ID   " +
                    "            WHERE dm.DOJO_ID = :dojoId   " +
                    "            ORDER BY  dm.CREATED_AT DESC ;")
    Page<DojoMemberInf> findAllByDojo_Id(@Param("dojoId") Long dojoId, Pageable pageable);
}
