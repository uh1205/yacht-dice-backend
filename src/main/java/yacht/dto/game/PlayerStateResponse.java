package yacht.dto.game;

import java.util.Map;
import lombok.Builder;

@Builder
public record PlayerStateResponse(
        String playerId,
        boolean isUpperFull,
        boolean bonusAwarded,
        int subtotal,
        int total,
        Map<String, Integer> scores
) {
}
