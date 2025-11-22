package yacht.dto.game;

import java.util.List;
import java.util.Map;
import lombok.Builder;

@Builder
public record GameStateResponse(
        String type,
        boolean isGameOver,
        boolean isRolled,
        int currentRound,
        int remainingRollCount,
        String currentPlayerId,
        List<Integer> diceValues,
        List<Boolean> diceLocks,
        Map<String, Integer> possibleScores,
        List<PlayerStateResponse> playerStates
) {
}
