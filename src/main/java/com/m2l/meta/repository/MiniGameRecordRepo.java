package com.m2l.meta.repository;

import com.m2l.meta.entity.MiniGameRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MiniGameRecordRepo extends JpaRepository<MiniGameRecord, Long> {

    MiniGameRecord findAllByCharacter_User_IdAndAndMiniGame_Id(@Param("userId") Long userId, @Param("miniGameId") Long miniGameId);

    @Query(value = "select * from mini_game_record r where year(UPDATED_AT) = year(:dateTime) " +
            " and month(UPDATED_AT) = month(:dateTime) and day(UPDATED_AT) = year(:dateTime) " +
            " and GAME_ID = :gameId order by RECORD desc ",
            countQuery = "select * from mini_game_record r where year(UPDATED_AT) = year(:dateTime) " +
                    " and month(UPDATED_AT) = month(:dateTime) and day(UPDATED_AT) = year(:dateTime) " +
                    " and GAME_ID = :gameId order by RECORD desc ", nativeQuery = true)
    Page<MiniGameRecord> findBestRecordDaily(@Param("dateTime") LocalDateTime dateTime,@Param("gameId") Long gameId, Pageable pageable);
}
