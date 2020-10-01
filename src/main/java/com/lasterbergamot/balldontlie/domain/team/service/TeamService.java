package com.lasterbergamot.balldontlie.domain.team.service;

import java.util.concurrent.ExecutionException;

public interface TeamService {
    void getAllTeams() throws ExecutionException, InterruptedException;
}
