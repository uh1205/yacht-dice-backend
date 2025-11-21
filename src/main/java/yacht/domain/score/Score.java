package yacht.domain.score;

import lombok.Getter;

public class Score {

    @Getter
    private final int value;

    private Score(int value) {
        if (value < 0) {
            throw new IllegalStateException("점수는 0 미만일 수 없습니다.");
        }
        this.value = value;
    }

    public static Score of(int value) {
        return new Score(value);
    }

    public static Score ofZero() {
        return new Score(0);
    }

    public boolean isGreaterEqual(int value) {
        return this.value >= value;
    }

}
