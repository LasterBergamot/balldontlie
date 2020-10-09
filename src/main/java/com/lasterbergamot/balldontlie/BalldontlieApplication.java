package com.lasterbergamot.balldontlie;

import com.lasterbergamot.balldontlie.domain.config.AppConfig;
import com.lasterbergamot.balldontlie.domain.game.service.GameService;
import com.lasterbergamot.balldontlie.domain.player.service.PlayerService;
import com.lasterbergamot.balldontlie.domain.stats.service.StatsService;
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
	private final GameService gameService;
	private final StatsService statsService;
	private final AppConfig appConfig;

	public static void main(String[] args) {
		SpringApplication.run(BalldontlieApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!appConfig.isSkipImport()) {
			teamService.getAllTeamsFromBalldontlieAPI();
			playerService.getAllPlayersFromBalldontlieAPI();
			gameService.getAllGamesFromBalldontlieAPI();
			statsService.getAllStatsFromBalldontlieAPI();
		}
	}
}
