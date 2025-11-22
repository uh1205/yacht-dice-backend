package yacht.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import yacht.domain.game.Player;

@Repository
public class PlayerRepository {

    private final Map<String, Player> players = new ConcurrentHashMap<>();

    public boolean existsById(String playerId) {
        return players.containsKey(playerId);
    }

    public Player create(String playerId) {
        Player player = new Player(playerId);
        players.put(playerId, player);
        return player;
    }

    public Optional<Player> findById(String playerId) {
        return Optional.ofNullable(players.get(playerId));
    }

}
