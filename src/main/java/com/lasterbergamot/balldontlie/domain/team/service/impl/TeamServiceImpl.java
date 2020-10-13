package com.lasterbergamot.balldontlie.domain.team.service.impl;

import com.lasterbergamot.balldontlie.client.ThrottledRestTemplate;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.lasterbergamot.balldontlie.util.Constants.ERR_MSG_THE_GIVEN_DIVISION_IS_NOT_PART_OF_THE_GIVEN_CONFERENCE;
import static com.lasterbergamot.balldontlie.util.Constants.ERR_MSG_THE_TEAM_DTOWRAPPER_OBJECT_GOT_FROM_THE_API_WAS_NULL;
import static com.lasterbergamot.balldontlie.util.Constants.URL_TEAM_BALLDONTLIE_API_PER_PAGE_100;

@Service
@Slf4j
@AllArgsConstructor
public class TeamServiceImpl implements TeamService, DataImporter {

    private final TeamRepository teamRepository;
    private final TeamTransformer teamTransformer;
    private final ThrottledRestTemplate restTemplate;

    @Override
    public void getAllTeamsFromBalldontlieAPI() {
        TeamDTOWrapper teamDTOWrapper = restTemplate.getForObject(URL_TEAM_BALLDONTLIE_API_PER_PAGE_100, TeamDTOWrapper.class);

        Optional.ofNullable(teamDTOWrapper)
                .ifPresentOrElse(this::handlePossibleNewTeams, () -> log.error(ERR_MSG_THE_TEAM_DTOWRAPPER_OBJECT_GOT_FROM_THE_API_WAS_NULL));
    }

    @Override
    public List<Team> getTeams(final Optional<Integer> count, final Conference conference, final Division division) {
        List<Team> teams = teamRepository.findAll();
        Predicate<Team> teamPredicate = getTeamPredicateAccordingToConferenceAndDivision(conference, division);

        return handleCount(count, teams, teamPredicate) ;
    }

    private Predicate<Team> getTeamPredicateAccordingToConferenceAndDivision(final Conference conference, final Division division) throws TeamQueryException {

        if (division != null && !division.getConference().equals(conference)) {
            throw new TeamQueryException(ERR_MSG_THE_GIVEN_DIVISION_IS_NOT_PART_OF_THE_GIVEN_CONFERENCE);
        }

        Predicate<Team> divisionPredicate = division == null ? team -> true : team -> team.getDivision().equals(division);
        Predicate<Team> conferencePredicate = conference == null ? team -> true : team -> team.getConference().equals(conference);

        return divisionPredicate.and(conferencePredicate);
    }

    private List<Team> handleCount(final Optional<Integer> count, final List<Team> teams, final Predicate<Team> teamPredicate) {
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

    private void handlePossibleNewTeams(final TeamDTOWrapper teamDTOWrapper) {
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
