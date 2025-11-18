package yacht.domain.score;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import yacht.domain.score.category.CategoryType;

public class ScoreBoard {

    public static final int BONUS_THRESHOLD = 63;
    public static final int BONUS_SCORE = 35;

    private final Map<CategoryType, Score> scores = new EnumMap<>(CategoryType.class);
    private boolean bonusAwarded = false;

    public ScoreBoard() {
        Arrays.stream(CategoryType.values())
                .forEach(type -> scores.put(type, null));
    }

    public void recordScore(CategoryType type, Score score) {
        if (scores.get(type) != null) {
            throw new IllegalStateException("이미 점수가 기록된 족보입니다.");
        }
        scores.put(type, score);
        checkBonus();
    }

    private void checkBonus() {
        if (bonusAwarded) {
            return;
        }
        if (subtotal() >= BONUS_THRESHOLD) {
            bonusAwarded = true;
        }
    }

    public int subtotal() {
        return scores.entrySet().stream()
                .filter(e -> e.getKey().isUpper() && e.getValue() != null)
                .mapToInt(e -> e.getValue().value())
                .sum();
    }

    public int total() {
        int baseScore = calculateBaseScore();
        if (bonusAwarded) {
            return baseScore + BONUS_SCORE;
        }
        return baseScore;
    }

    private int calculateBaseScore() {
        return scores.values().stream()
                .filter(Objects::nonNull)
                .mapToInt(Score::value)
                .sum();
    }

}
