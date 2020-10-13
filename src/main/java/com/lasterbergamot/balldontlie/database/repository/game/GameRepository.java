package com.lasterbergamot.balldontlie.database.repository.game;

import com.lasterbergamot.balldontlie.database.model.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import static com.lasterbergamot.balldontlie.util.Constants.QUERY_SEQ_GAME_ID_NEXTVAL;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    @Query(value = QUERY_SEQ_GAME_ID_NEXTVAL, nativeQuery = true)
    Integer getNextId();
}