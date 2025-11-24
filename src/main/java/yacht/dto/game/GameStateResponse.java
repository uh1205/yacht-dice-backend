package yacht.dto.game;

import java.util.List;
import java.util.Map;
import lombok.Builder;

@Builder
public record GameStateResponse(
        String type,
        int totalRounds,
        int bonusScore,
        int bonusThreshold,
        List<String> upperCategories,
        List<String> lowerCategories,
        List<PlayerInfo> players,
        PlayerInfo currentPlayer,
        boolean isGameOver,
        boolean isRolled,
        int currentRound,
        int remainingRollCount,
        List<Integer> diceValues,
        List<Boolean> diceLocks,
        Map<String, Integer> possibleScores,
        List<PlayerStateResponse> playerStates,
        boolean isDraw,
        PlayerInfo winner
) {
}
