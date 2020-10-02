package com.lasterbergamot.balldontlie.domain.team.transform;

import com.lasterbergamot.balldontlie.database.model.team.Team;
import com.lasterbergamot.balldontlie.domain.team.model.TeamDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamTransformer {

    public List<Team> transformTeamDTOListToTeamList(List<TeamDTO> teamDTOList) {
        return teamDTOList
                .stream()
                .map(this::transformTeamDTOToTeam)
                .collect(Collectors.toUnmodifiableList());
    }

    public Team transformTeamDTOToTeam(TeamDTO teamDTO) {
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
