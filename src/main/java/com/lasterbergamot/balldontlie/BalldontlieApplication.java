package com.lasterbergamot.balldontlie;

import com.lasterbergamot.balldontlie.domain.player.service.PlayerService;
import com.lasterbergamot.balldontlie.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class BalldontlieApplication implements CommandLineRunner {

	private final TeamService teamService;
	private final PlayerService playerService;

	public static void main(String[] args) {
		SpringApplication.run(BalldontlieApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		teamService.getAllTeamsFromBalldontlieAPI();
		playerService.getAllPlayersFromBalldontlieAPI();
	}
}
