package yacht.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yacht.domain.game.Player;
import yacht.dto.join.JoinResponse;
import yacht.repository.PlayerRepository;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public JoinResponse register(String nickname) {
        Player player = playerRepository.save(nickname);
        return new JoinResponse(player.getPlayerId(), player.getNickname());
    }

}
