package com.lasterbergamot.balldontlie.domain.team.service.impl;

import com.lasterbergamot.balldontlie.database.model.team.Team;
import com.lasterbergamot.balldontlie.database.repository.team.TeamRepository;
import com.lasterbergamot.balldontlie.domain.team.model.TeamDTO;
import com.lasterbergamot.balldontlie.domain.team.model.TeamDTOWrapper;
import com.lasterbergamot.balldontlie.domain.team.service.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final RestTemplate restTemplate;

    @Override
    public void getAllTeams() {
        log.info("Getting all teams!");
        List<Team> currentlyStoredTeams = teamRepository.findAll();
        TeamDTOWrapper teamDTOWrapper = restTemplate.getForObject("https://www.balldontlie.io/api/v1/teams/", TeamDTOWrapper.class);

        if (teamDTOWrapper != null) {
            if (currentlyStoredTeams.size() < teamDTOWrapper.getMeta().getTotalCount()) {
                log.info("New teams available!");

                List<Team> teamsFromAPI = transformTeamDTOListToTeamList(teamDTOWrapper.getTeamDTOs());
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

    private List<Team> transformTeamDTOListToTeamList(List<TeamDTO> teamDTOList) {
        return teamDTOList
                .stream()
                .map(this::transformTeamDTOToTeam)
                .collect(Collectors.toList());
    }

    private Team transformTeamDTOToTeam(TeamDTO teamDTO) {
        return Team.builder()
                .id(teamDTO.getId())
                .abbreviation(teamDTO.getAbbreviation())
                .city(teamDTO.getCity())
                .conference(teamDTO.getConference())
                .division(teamDTO.getDivision())
                .fullName(teamDTO.getFullName())
                .name(teamDTO.getName())
                .build();
    }
}
