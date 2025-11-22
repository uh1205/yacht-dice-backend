package yacht.domain.game;

import java.util.ArrayList;
import java.util.List;
import yacht.domain.score.Score;
import yacht.domain.score.category.ScoreCategory;

public class Participants {

    private final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;

    public void add(Player player) {
        players.add(player);
    }

    public void assertPlayerTurn(Player player) {
        String playerId = player.getPlayerId();
        String currentPlayerId = getCurrentPlayer().getPlayerId();
        if (!playerId.equals(currentPlayerId)) {
            throw new IllegalStateException("해당 플레이어의 턴이 아닙니다.");
        }
    }

    public void recordScore(ScoreCategory category, Score score) {
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.recordScore(category, score);
    }

    public void passTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public int getPlayerCount() {
        return players.size();
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

}
