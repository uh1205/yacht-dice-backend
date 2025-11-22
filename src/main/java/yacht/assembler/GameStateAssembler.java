package yacht.assembler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import yacht.domain.dice.DiceValue;
import yacht.domain.game.GameRoom;
import yacht.domain.game.Player;
import yacht.domain.score.Score;
import yacht.domain.score.category.ScoreCategory;
import yacht.dto.GameMessageType;
import yacht.dto.game.GameStateResponse;
import yacht.dto.game.PlayerInfo;
import yacht.dto.game.PlayerStateResponse;

@Component
public class GameStateAssembler {

    public GameStateResponse toResponse(GameRoom room) {
        return GameStateResponse.builder()
                .type(GameMessageType.GAME_STATE.name())
                .totalRounds(room.getTotalRounds())
                .bonusScore(room.getBonusScore())
                .bonusThreshold(room.getBonusThreshold())
                .upperCategories(toCategoryNames(room.getUpperCategories()))
                .lowerCategories(toCategoryNames(room.getLowerCategories()))
                .players(toPlayerInfos(room.getPlayers()))
                .currentPlayer(toPlayerInfo(room.getCurrentPlayer()))
                .isGameOver(room.isGameOver())
                .isRolled(room.isRolled())
                .currentRound(room.getCurrentRound())
                .remainingRollCount(room.getRemainingRollCount())
                .diceValues(toDiceValues(room.getDiceValues()))
                .diceLocks(room.getDiceLocks())
                .possibleScores(toPossibleScores(room.computePossibleScores()))
                .playerStates(toPlayerStates(room))
                .build();
    }

    private List<String> toCategoryNames(List<ScoreCategory> categories) {
        return categories.stream()
                .map(ScoreCategory::getCategoryName)
                .toList();
    }

    private List<PlayerInfo> toPlayerInfos(List<Player> players) {
        return players.stream()
                .map(this::toPlayerInfo)
                .toList();
    }

    private PlayerInfo toPlayerInfo(Player player) {
        return new PlayerInfo(player.getPlayerId(), player.getNickname());
    }

    private List<Integer> toDiceValues(List<DiceValue> diceValues) {
        return diceValues.stream()
                .map(DiceValue::getValue)
                .toList();
    }

    private Map<String, Integer> toPossibleScores(Map<ScoreCategory, Score> possibleScores) {
        return possibleScores.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getCategoryName(),
                        e -> e.getValue().getValue()
                ));
    }

    private List<PlayerStateResponse> toPlayerStates(GameRoom room) {
        return room.getPlayers().stream()
                .map(player ->
                        PlayerStateResponse.builder()
                                .playerId(player.getPlayerId())
                                .isUpperFull(player.isUpperFull())
                                .bonusAwarded(player.isBonusAwarded())
                                .subtotal(player.getSubtotal().getValue())
                                .total(player.getTotal().getValue())
                                .scores(toScores(player.getScores()))
                                .build()
                )
                .toList();
    }

    private Map<String, Integer> toScores(Map<ScoreCategory, Score> scores) {
        return scores.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getCategoryName(),
                        e -> e.getValue().getValue()
                ));
    }

}
