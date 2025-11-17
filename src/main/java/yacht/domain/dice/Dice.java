package yacht.domain.dice;

import lombok.Getter;
import yacht.domain.dice.strategy.DiceRollStrategy;

public class Dice {

    public static final int MIN_VALUE = 1;
    public static final int MAX_VALUE = 6;

    private final DiceRollStrategy rollStrategy;

    @Getter
    private Integer value;
    private boolean locked = false;

    public Dice(DiceRollStrategy rollStrategy) {
        this.rollStrategy = rollStrategy;
    }

    public void roll() {
        if (!this.locked) {
            updateValue();
        }
    }

    private void updateValue() {
        int newValue = rollStrategy.roll(MIN_VALUE, MAX_VALUE);
        validateValue(newValue);
        this.value = newValue;
    }

    private void validateValue(int value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalStateException("주사위가 가질 수 있는 값의 범위를 벗어났습니다.");
        }
    }

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }

    public void reset() {
        this.value = null;
        this.locked = false;
    }

}
