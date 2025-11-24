package yacht.domain.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import yacht.domain.score.Score;
import yacht.domain.score.category.ScoreCategory;

public class Participants {

    private final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;

    /**
     * 플레이어를 추가합니다.
     *
     * @param player 추가할 플레이어
     */
    public void add(Player player) {
        players.add(player);
    }

    /**
     * 현재 플레이어의 턴이 맞는지 확인합니다.
     *
     * @param player 확인할 플레이어
     */
    public void assertPlayerTurn(Player player) {
        String playerId = player.getPlayerId();
        String currentPlayerId = getCurrentPlayer().getPlayerId();
        if (!playerId.equals(currentPlayerId)) {
            throw new IllegalStateException("해당 플레이어의 턴이 아닙니다.");
        }
    }

    /**
     * 현재 플레이어의 점수를 기록합니다.
     *
     * @param category 기록할 족보
     * @param score    기록할 점수
     */
    public void recordScore(ScoreCategory category, Score score) {
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.recordScore(category, score);
    }

    /**
     * 다음 플레이어로 순서를 넘깁니다.
     */
    public void passTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    /**
     * 무승부 여부를 반환합니다.
     *
     * @return 무승부 여부
     */
    public boolean isDraw() {
        Map<Score, Long> scoreCounts = players.stream()
                .collect(Collectors.groupingBy(
                        Player::getTotal,
                        Collectors.counting()
                ));
        Score maxScore = scoreCounts.keySet().stream()
                .max(Score::compareTo)
                .orElse(Score.ofZero());
        return scoreCounts.getOrDefault(maxScore, 0L) >= 2;
    }

    /**
     * 플레이어의 수를 반환합니다.
     *
     * @return 플레이어 수
     */
    public int getPlayerCount() {
        return players.size();
    }

    /**
     * 현재 플레이어를 반환합니다.
     *
     * @return 현재 플레이어
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * 현재 총합 득점이 가장 높은 플레이어를 반환합니다.
     *
     * @return 총합 득점이 가장 높은 플레이어
     */
    public Player getLeadingPlayer() {
        return players.stream()
                .max(Comparator.comparing(Player::getTotal))
                .orElseThrow();
    }

    /**
     * 모든 플레이어를 반환합니다.
     *
     * @return 모든 플레이어
     */
    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

}
