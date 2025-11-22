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
        String currentPlayerId,
        List<String> playerIds,
        List<String> upperCategories,
        List<String> lowerCategories
) {
}
