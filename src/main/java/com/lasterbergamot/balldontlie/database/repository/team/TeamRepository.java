package com.lasterbergamot.balldontlie.database.repository.team;

import com.lasterbergamot.balldontlie.database.model.team.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends MongoRepository<Team, Integer> {}