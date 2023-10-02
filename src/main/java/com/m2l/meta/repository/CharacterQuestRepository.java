package com.m2l.meta.repository;

import com.m2l.meta.entity.CharacterQuestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CharacterQuestRepository extends JpaRepository<CharacterQuestDetail, Long> {

    @Query(value = "SELECT cqd.* FROM users u join user_character c on u.ID = c.USER_ID AND u.USERNAME = :username " +
            "join character_quest_detail cqd on c.CHARACTER_ID = cqd.CHARACTER_ID and cqd.QUEST_ID = :questId ", nativeQuery = true)
    CharacterQuestDetail getQuestDetailForCurrentUser(@Param("username") String username, @Param("questId") Long questId);

    @Query(value = "SELECT cqd.* FROM users u join user_character c on u.ID = c.USER_ID AND u.USERNAME = :username " +
            "join character_quest_detail cqd on c.CHARACTER_ID = cqd.CHARACTER_ID and cqd.QUEST_ID = :questId " +
            "AND (cqd.IS_ACCEPT = :acceptStatus OR ISNULL(:acceptStatus)) " +
            "AND (cqd.IS_COMPLETED = :completedStatus OR ISNULL(:completedStatus))  " +
            "AND (cqd.IS_FINISHED = :finishedStatus OR ISNULL(:finishedStatus)) ", nativeQuery = true)
    CharacterQuestDetail getQuestDetailForCurrentUserAcceptable(@Param("username") String username,
                                                                @Param("questId") Long questId,
                                                                @Param("acceptStatus") Boolean acceptStatus,
                                                                @Param("completedStatus") Boolean completedStatus,
                                                                @Param("finishedStatus") Boolean finishedStatus);

    List<CharacterQuestDetail> findAllByCharacter_CharacterId(Long characterId);
}
