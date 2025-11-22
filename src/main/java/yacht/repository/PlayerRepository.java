package yacht.repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import yacht.domain.game.Player;

@Repository
public class PlayerRepository {

    private final Map<String, Player> players = new ConcurrentHashMap<>();

    public Player save(String nickname) {
        String playerId = createPlayerId();
        Player player = new Player(playerId, nickname);
        players.put(playerId, player);
        return player;
    }

    private String createPlayerId() {
        return "player-" + UUID.randomUUID();
    }

    public Optional<Player> findById(String playerId) {
        return Optional.ofNullable(players.get(playerId));
    }

}
