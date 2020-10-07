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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamTransformer teamTransformer;
    private final RestTemplate restTemplate;

    @Override
    public void getAllTeamsFromBalldontlieAPI() {
        TeamDTOWrapper teamDTOWrapper = restTemplate.getForObject("https://www.balldontlie.io/api/v1/teams?per_page=100", TeamDTOWrapper.class);

        Optional.ofNullable(teamDTOWrapper)
                .ifPresentOrElse(this::handlePossibleNewTeams, () -> log.error("The TeamDTOWrapper object got from the API was null!"));
    }

    @Override
    public List<Team> getTeams(final int count) {
        List<Team> teams = teamRepository.findAll();

        return count == -1
                ? teams
                : teams.stream().limit(count).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Team> getTeam(final int id) {
        return teamRepository.findById(id);
    }

    private void handlePossibleNewTeams(TeamDTOWrapper teamDTOWrapper) {
        List<Team> currentlyStoredTeams = teamRepository.findAll();

        log.info("Checking for possible new teams!");
        if (currentlyStoredTeams.size() < teamDTOWrapper.getMeta().getTotalCount()) {
            List<Team> teamsFromAPI = teamTransformer.transformTeamDTOListToTeamList(teamDTOWrapper.getTeamDTOs());
            Set<Team> teamsToSave = new HashSet<>(currentlyStoredTeams);
            teamsToSave.addAll(teamsFromAPI);

            if (teamsToSave.size() > 0) {
                log.info("New teams available!");
                teamRepository.saveAll(List.copyOf(teamsToSave));
                log.info("Saved {} new teams!", teamsToSave.size());
            } else {
                log.info("No new teams available!");
            }
        } else {
            log.info("No new teams available!");
        }
    }
}
