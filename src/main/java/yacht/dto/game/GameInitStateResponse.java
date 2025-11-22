package yacht.dto.game;

import java.util.List;
import lombok.Builder;

@Builder
public record GameInitStateResponse(
        String type,
        int maxRollCount,
        int totalRounds,
        int bonusScore,
        int bonusThreshold,
        PlayerInfo currentPlayer,
        List<String> upperCategories,
        List<String> lowerCategories,
        List<PlayerInfo> players
) {
}
