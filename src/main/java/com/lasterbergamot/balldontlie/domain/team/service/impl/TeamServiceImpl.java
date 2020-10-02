package com.lasterbergamot.balldontlie.domain.team.service.impl;

import com.lasterbergamot.balldontlie.database.model.team.Team;
import com.lasterbergamot.balldontlie.database.repository.team.TeamRepository;
import com.lasterbergamot.balldontlie.domain.team.model.TeamDTOWrapper;
import com.lasterbergamot.balldontlie.domain.team.service.TeamService;
import com.lasterbergamot.balldontlie.domain.team.transform.TeamTransformer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamTransformer teamTransformer;
    private final RestTemplate restTemplate;

    @Override
    public void getAllTeamsFromBalldontlieAPI() {
        log.info("Getting all teams!");
        TeamDTOWrapper teamDTOWrapper = restTemplate.getForObject("https://www.balldontlie.io/api/v1/teams/", TeamDTOWrapper.class);

        if (teamDTOWrapper != null) {
            List<Team> currentlyStoredTeams = teamRepository.findAll();

            if (currentlyStoredTeams.size() < teamDTOWrapper.getMeta().getTotalCount()) {
                log.info("New teams available!");

                List<Team> teamsFromAPI = teamTransformer.transformTeamDTOListToTeamList(teamDTOWrapper.getTeamDTOs());
                teamsFromAPI.removeAll(currentlyStoredTeams);

                log.info("New teams: " + teamsFromAPI);
                teamRepository.saveAll(teamsFromAPI);
            } else {
                log.info("No new teams available!");
            }
        } else {
            log.error("The TeamDTOWrapper object got from the API was null!");
        }
    }
}
