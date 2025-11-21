package yacht.domain.dice;

import lombok.Getter;

@Getter
public class Dice {

    private DiceValue value;
    private boolean locked;

    public Dice() {
        this.value = DiceValue.getRandomValue();
        this.locked = false;
    }

    /**
     * 주사위를 굴립니다.
     */
    public void roll() {
        if (this.locked) {
            return;
        }
        this.value = DiceValue.getRandomValue();
    }

    /**
     * 주사위를 고정합니다.
     */
    public void lock() {
        this.locked = true;
    }

    /**
     * 주사위를 고정 해제합니다.
     */
    public void unlock() {
        this.locked = false;
    }

    /**
     * 주사위의 고정 여부를 초기화합니다.
     */
    public void reset() {
        this.locked = false;
    }

}
