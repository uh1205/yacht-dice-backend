package yacht.domain.game;

import java.util.Map;
import lombok.Getter;
import yacht.domain.score.Score;
import yacht.domain.score.ScoreBoard;
import yacht.domain.score.category.ScoreCategory;

public class Player {

    @Getter
    private final String playerId;

    @Getter
    private final String nickname;

    private final ScoreBoard scoreBoard = new ScoreBoard();

    public Player(String playerId, String nickname) {
        this.playerId = playerId;
        this.nickname = nickname;
    }

    /**
     * 점수표에 점수를 기록합니다.
     *
     * @param category 기록할 족보
     * @param score    점수
     */
    public void recordScore(ScoreCategory category, Score score) {
        scoreBoard.recordScore(category, score);
    }

    /**
     * 상위 족보의 점수를 모두 기록했는지 여부를 반환합니다.
     *
     * @return 상위 족보 점수 전체 기록 여부
     */
    public boolean isUpperFull() {
        return scoreBoard.isUpperFull();
    }

    /**
     * 보너스 점수 수여 여부를 반환합니다.
     *
     * @return 보너스 점수 수여 여부
     */
    public boolean isBonusAwarded() {
        return scoreBoard.isBonusAwarded();
    }

    /**
     * 점수의 소계를 반환합니다.
     *
     * @return 점수 소계
     */
    public Score getSubtotal() {
        return scoreBoard.subtotal();
    }

    /**
     * 점수의 총합을 반환합니다. (보너스 점수 포함)
     *
     * @return 점수 총합
     */
    public Score getTotal() {
        return scoreBoard.total();
    }

    /**
     * 전체 족보별 점수를 반환합니다.
     *
     * @return 전체 족보별 점수
     */
    public Map<ScoreCategory, Score> getScores() {
        return scoreBoard.getScores();
    }

    public void clearScoreBoard() {
        scoreBoard.clear();
    }

}
