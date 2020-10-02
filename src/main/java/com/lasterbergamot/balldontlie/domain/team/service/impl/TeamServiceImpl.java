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

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final RestTemplate restTemplate;

    @PostConstruct
    @Override
    public void getAllTeams() {
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

    public void asd() throws ExecutionException, InterruptedException {
        log.info("Getting all teams!");
        List<Team> currentlySavedTeams = teamRepository.findAll();
        System.out.println("currentlySavedTeams: " + currentlySavedTeams);

        List<CompletableFuture<TeamDTO>> completableFutureList = new ArrayList<>();
        for (int index = 1; index <= 30; index++) {
            int innerIndex = index;
            completableFutureList.add(
                    CompletableFuture.supplyAsync(
                            () -> restTemplate.getForObject("https://www.balldontlie.io/api/v1/teams/" + innerIndex, TeamDTO.class)
                    )
            );
        }

        // wait until all the threads in the arguments get completed
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                completableFutureList.toArray(new CompletableFuture[0])
        );

        // after the completion of execution for all threads, collect all the return values from all the threads
        CompletableFuture<List<TeamDTO>> allCompletableFuture = allFutures.thenApply(
                future -> completableFutureList
                    .stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList())
        );

        CompletableFuture<List<TeamDTO>> completableFuture = allCompletableFuture.toCompletableFuture();
        List<TeamDTO> teamsFromAPI = completableFuture.get();

        AtomicInteger counter = new AtomicInteger();
        teamsFromAPI.forEach(teamDTO -> {
            Team team = transformTeamDTOToTeam(teamDTO);
            if (!currentlySavedTeams.contains(team)) {
                log.info("Adding team to db: " + teamDTO);
                teamRepository.save(team);
                counter.getAndIncrement();
            }
        });

        System.out.println(counter + " new Teams have been saved!");
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
