package com.lasterbergamot.balldontlie.domain.team.service.impl;

import com.lasterbergamot.balldontlie.database.model.team.Conference;
import com.lasterbergamot.balldontlie.database.model.team.Division;
import com.lasterbergamot.balldontlie.database.model.team.Team;
import com.lasterbergamot.balldontlie.database.repository.team.TeamRepository;
import com.lasterbergamot.balldontlie.domain.DataImporter;
import com.lasterbergamot.balldontlie.domain.team.model.TeamDTOWrapper;
import com.lasterbergamot.balldontlie.domain.team.service.TeamService;
import com.lasterbergamot.balldontlie.domain.team.transform.TeamTransformer;
import com.lasterbergamot.balldontlie.graphql.team.exception.TeamQueryException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TeamServiceImpl implements TeamService, DataImporter {

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
    public List<Team> getTeams(final Optional<Integer> count, final Conference conference, final Division division) {
        List<Team> teams = teamRepository.findAll();
        Predicate<Team> teamPredicate = getTeamPredicateAccordingToConferenceAndDivision(conference, division);

        return handleCount(count, teams, teamPredicate) ;
    }

    private Predicate<Team> getTeamPredicateAccordingToConferenceAndDivision(final Conference conference, final Division division) throws TeamQueryException {

        if (division != null && !division.getConference().equals(conference)) {
            throw new TeamQueryException("The given division is not part of the given conference!");
        }

        Predicate<Team> divisionPredicate = division == null ? team -> true : team -> team.getDivision().equals(division);
        Predicate<Team> conferencePredicate = conference == null ? team -> true : team -> team.getConference().equals(conference);

        return divisionPredicate.and(conferencePredicate);
    }

    private List<Team> handleCount(final Optional<Integer> count, final List<Team> teams, Predicate<Team> teamPredicate) {
        return teams
                .stream()
                .filter(teamPredicate)
                .limit(count.orElse(Integer.MAX_VALUE))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Team> getTeam(final int id) {
        return teamRepository.findById(id);
    }

    @Override
    public void doImport() {
        getAllTeamsFromBalldontlieAPI();
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
