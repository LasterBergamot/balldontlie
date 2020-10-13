package com.lasterbergamot.balldontlie.domain.player.transform;

import com.lasterbergamot.balldontlie.database.model.player.Player;
import com.lasterbergamot.balldontlie.domain.player.model.PlayerDTO;
import com.lasterbergamot.balldontlie.domain.team.transform.TeamTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlayerTransformer {

    private final TeamTransformer teamTransformer;

    public List<Player> transformPlayerDTOListToPlayerList(final List<PlayerDTO> playerDTOS) {
        return playerDTOS
                .stream()
                .map(this::transformPlayerDTOToPlayer)
                .collect(Collectors.toUnmodifiableList());
    }

    private Player transformPlayerDTOToPlayer(final PlayerDTO playerDTO) {
        return Player.builder()
                .id(playerDTO.getId())
                .firstName(playerDTO.getFirstName())
                .lastName(playerDTO.getLastName())
                .heightFeet(playerDTO.getHeightFeet())
                .heightInches(playerDTO.getHeightInches())
                .position(playerDTO.getPosition())
                .weightPounds(playerDTO.getWeightPounds())
                .team(teamTransformer.transformTeamDTOToTeam(playerDTO.getTeam()))
                .build();
    }
}
