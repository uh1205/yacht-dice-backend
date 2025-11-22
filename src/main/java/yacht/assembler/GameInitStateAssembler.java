package yacht.assembler;

import java.util.List;
import org.springframework.stereotype.Component;
import yacht.domain.game.GameRoom;
import yacht.domain.game.Player;
import yacht.domain.score.category.ScoreCategory;
import yacht.dto.GameMessageType;
import yacht.dto.game.GameInitStateResponse;

@Component
public class GameInitStateAssembler {

    public GameInitStateResponse toResponse(GameRoom room) {
        return GameInitStateResponse.builder()
                .type(GameMessageType.INIT_GAME.name())
                .maxRollCount(room.getMaxRollCount())
                .totalRounds(room.getTotalRounds())
                .bonusScore(room.getBonusScore())
                .bonusThreshold(room.getBonusThreshold())
                .currentPlayerId(room.getCurrentPlayer().getPlayerId())
                .playerIds(toPlayerIds(room.getPlayers()))
                .upperCategories(toCategoryNames(room.getUpperCategories()))
                .lowerCategories(toCategoryNames(room.getLowerCategories()))
                .build();
    }

    private List<String> toPlayerIds(List<Player> players) {
        return players.stream()
                .map(Player::getPlayerId)
                .toList();
    }

    private List<String> toCategoryNames(List<ScoreCategory> categories) {
        return categories.stream()
                .map(ScoreCategory::getCategoryName)
                .toList();
    }

}
