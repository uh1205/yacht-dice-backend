package yacht.domain.score;

public record Score(int value) implements Comparable<Score> {

    private static final Score ZERO = new Score(0);

    public Score {
        if (value < 0) {
            throw new IllegalArgumentException("점수는 0 미만일 수 없습니다: " + value);
        }
    }

    public static Score of(int value) {
        if (value == 0) {
            return ZERO;
        }
        return new Score(value);
    }

    public static Score ofZero() {
        return ZERO;
    }

    @Override
    public int compareTo(Score other) {
        return Integer.compare(this.value, other.value);
    }

}
