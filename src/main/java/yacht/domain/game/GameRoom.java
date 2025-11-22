package yacht.domain.game;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import yacht.domain.dice.DiceSet;
import yacht.domain.dice.DiceValue;
import yacht.domain.score.Score;
import yacht.domain.score.ScoreBoard;
import yacht.domain.score.category.ScoreCategory;

public class GameRoom {

    public static final int MAX_ROLL_COUNT = 3;

    @Getter
    private final String roomId;
    private final DiceSet diceSet = new DiceSet();
    private final Participants participants = new Participants();

    @Getter
    private int remainingRollCount = MAX_ROLL_COUNT;

    @Getter
    private boolean isRolled = false;
    private int turnCount = 0;

    public GameRoom(String roomId) {
        this.roomId = roomId;
    }

    /**
     * 플레이어를 추가합니다.
     *
     * @param player 추가할 플레이어
     */
    public void addPlayer(Player player) {
        participants.add(player);
    }

    /**
     * 플레이어의 턴인지 확인합니다.
     *
     * @param player 확인할 플레이어
     */
    public void assertPlayerTurn(Player player) {
        participants.assertPlayerTurn(player);
    }

    /**
     * 특정 인덱스의 주사위를 고정합니다.
     *
     * @param diceIndex 고정할 주사위의 인덱스
     */
    public void lockDice(int diceIndex) {
        diceSet.lock(diceIndex);
    }

    /**
     * 특정 인덱스의 주사위를 고정 해제합니다.
     *
     * @param diceIndex 고정 해제할 주사위의 인덱스
     */
    public void unlockDice(int diceIndex) {
        diceSet.unlock(diceIndex);
    }

    /**
     * 주사위를 굴립니다. (고정된 주사위만 굴려집니다.)
     */
    public void rollDice() {
        assertCanRollDice();
        diceSet.roll();
        remainingRollCount--;
        isRolled = true;
    }

    private void assertCanRollDice() {
        if (remainingRollCount <= 0) {
            throw new IllegalStateException("더 이상 주사위를 굴릴 수 없습니다.");
        }
    }

    /**
     * 특정 족보에 점수를 기록합니다.
     *
     * @param category 선택된 족보
     */
    public void recordScore(ScoreCategory category) {
        Score score = category.calculateScore(diceSet);
        participants.recordScore(category, score);
        passTurn();
    }

    private void passTurn() {
        participants.passTurn();
        diceSet.reset();
        remainingRollCount = MAX_ROLL_COUNT;
        turnCount++;
        isRolled = false;
    }

    /**
     * 게임 종료 여부를 반환합니다.
     *
     * @return 게임 종료 여부
     */
    public boolean isGameOver() {
        return getCurrentRound() > getTotalRounds();
    }

    /**
     * 주사위를 굴릴 수 있는 최대 횟수를 반환합니다.
     *
     * @return 최대 주사위 굴림 횟수
     */
    public int getMaxRollCount() {
        return MAX_ROLL_COUNT;
    }

    /**
     * 전체 라운드 수를 반환합니다. (전체 족보 개수와 동일합니다.)
     *
     * @return 전체 라운드 수
     */
    public int getTotalRounds() {
        return ScoreCategory.values().length;
    }

    /**
     * 보너스 점수를 반환합니다.
     *
     * @return 보너스 점수
     */
    public int getBonusScore() {
        return ScoreBoard.BONUS_SCORE;
    }

    /**
     * 보너스 점수를 받기위한 소계의 기준치를 반환합니다.
     *
     * @return 보너스 점수 기준치
     */
    public int getBonusThreshold() {
        return ScoreBoard.BONUS_THRESHOLD;
    }

    /**
     * 현재 라운드를 반환합니다.
     *
     * @return 현재 라운드
     */
    public int getCurrentRound() {
        return turnCount / participants.getPlayerCount() + 1;
    }

    /**
     * 현재 플레이어를 반환합니다.
     *
     * @return 현재 플레이어
     */
    public Player getCurrentPlayer() {
        return participants.getCurrentPlayer();
    }

    /**
     * 전체 플레이어를 반환합니다.
     *
     * @return 전체 플레이어
     */
    public List<Player> getPlayers() {
        return participants.getPlayers();
    }

    /**
     * 상위 족보 목록를 반환합니다.
     *
     * @return 상위 족보 목록
     */
    public List<ScoreCategory> getUpperCategories() {
        return Arrays.stream(ScoreCategory.values())
                .filter(ScoreCategory::isUpperCategory)
                .toList();
    }

    /**
     * 하위 족보 목록를 반환합니다.
     *
     * @return 하위 족보 목록
     */
    public List<ScoreCategory> getLowerCategories() {
        return Arrays.stream(ScoreCategory.values())
                .filter(ScoreCategory::isLowerCategory)
                .toList();
    }

    /**
     * 전체 주사위의 눈을 반환합니다.
     *
     * @return 전체 주사위 눈
     */
    public List<DiceValue> getDiceValues() {
        return diceSet.getDiceValues();
    }

    /**
     * 전체 주사위의 고정 여부를 반환합니다.
     *
     * @return 전체 주사위 고정 여부
     */
    public List<Boolean> getDiceLocks() {
        return diceSet.getDiceLocks();
    }

    /**
     * 현재 주사위의 조합으로 얻을 수 있는 족보별 점수를 반환합니다.
     *
     * @return 가능한 족보별 점수 조합
     */
    public Map<ScoreCategory, Score> computePossibleScores() {
        return Arrays.stream(ScoreCategory.values())
                .collect(Collectors.toMap(
                        category -> category,
                        category -> category.calculateScore(diceSet)
                ));
    }

}
