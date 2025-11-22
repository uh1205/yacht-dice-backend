package yacht.assembler;

import java.util.List;
import org.springframework.stereotype.Component;
import yacht.domain.game.GameRoom;
import yacht.domain.game.Player;
import yacht.domain.score.category.ScoreCategory;
import yacht.dto.GameMessageType;
import yacht.dto.game.GameInitStateResponse;
import yacht.dto.game.PlayerInfo;

@Component
public class GameInitStateAssembler {

    public GameInitStateResponse toResponse(GameRoom room) {
        return GameInitStateResponse.builder()
                .type(GameMessageType.INIT_GAME.name())
                .maxRollCount(room.getMaxRollCount())
                .totalRounds(room.getTotalRounds())
                .bonusScore(room.getBonusScore())
                .bonusThreshold(room.getBonusThreshold())
                .currentPlayer(toPlayerInfo(room.getCurrentPlayer()))
                .upperCategories(toCategoryNames(room.getUpperCategories()))
                .lowerCategories(toCategoryNames(room.getLowerCategories()))
                .players(toPlayerInfos(room.getPlayers()))
                .build();
    }

    private PlayerInfo toPlayerInfo(Player player) {
        return new PlayerInfo(player.getPlayerId(), player.getNickname());
    }

    private List<PlayerInfo> toPlayerInfos(List<Player> players) {
        return players.stream()
                .map(this::toPlayerInfo)
                .toList();
    }

    private List<String> toCategoryNames(List<ScoreCategory> categories) {
        return categories.stream()
                .map(ScoreCategory::getCategoryName)
                .toList();
    }

}
