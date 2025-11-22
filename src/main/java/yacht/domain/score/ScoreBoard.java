package yacht.domain.score;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import yacht.domain.score.category.ScoreCategory;

public class ScoreBoard {

    public static final int BONUS_SCORE = 35;
    public static final int BONUS_THRESHOLD = 63;

    private final Map<ScoreCategory, Score> scores = new EnumMap<>(ScoreCategory.class);

    public ScoreBoard() {
        Arrays.stream(ScoreCategory.values())
                .forEach(category -> scores.put(category, null));
    }

    /**
     * 특정 족보에 점수를 기록합니다.
     *
     * @param category 점수를 기록할 족보
     * @param score    기록할 점수
     */
    public void recordScore(ScoreCategory category, Score score) {
        assertNotRecorded(category);
        scores.put(category, score);
    }

    private void assertNotRecorded(ScoreCategory category) {
        if (scores.get(category) != null) {
            throw new IllegalStateException("이미 점수가 기록된 족보입니다: " + category.name());
        }
    }

    /**
     * 보너스 획득 여부를 판단합니다.
     *
     * @return 보너스 획득 여부
     */
    public boolean isBonusAwarded() {
        return subtotal().isGreaterEqual(BONUS_THRESHOLD);
    }

    /**
     * 소계를 계산하여 반환합니다.
     *
     * @return 소계
     */
    public Score subtotal() {
        return Score.of(sumUpperCategory());
    }

    private int sumUpperCategory() {
        return scores.entrySet().stream()
                .filter(e -> e.getKey().isUpperCategory() && e.getValue() != null)
                .mapToInt(e -> e.getValue().getValue())
                .sum();
    }

    /**
     * 총합 득점을 반환합니다. (보너스 점수 포함)
     *
     * @return 총합 득점
     */
    public Score total() {
        int baseScore = calculateBaseScore();
        if (isBonusAwarded()) {
            return Score.of(baseScore + BONUS_SCORE);
        }
        return Score.of(baseScore);
    }

    private int calculateBaseScore() {
        return scores.values().stream()
                .filter(Objects::nonNull)
                .mapToInt(Score::getValue)
                .sum();
    }

    /**
     * 상위 족보들의 점수를 전부 기록했는지 여부를 반환합니다.
     *
     * @return 상위 족보 전체 기록 여부
     */
    public boolean isUpperFull() {
        return scores.entrySet().stream()
                .filter(e -> e.getKey().isUpperCategory())
                .allMatch(e -> e.getValue() != null);
    }

    /**
     * 족보별 전체 점수를 반환합니다.
     *
     * @return 족보별 전체 점수
     */
    public Map<ScoreCategory, Score> getScores() {
        return scores.entrySet().stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void clear() {
        scores.replaceAll((category, score) -> null);
    }

}
