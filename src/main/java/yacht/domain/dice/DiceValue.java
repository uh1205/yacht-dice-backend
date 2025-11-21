package yacht.domain.dice;

import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;

@Getter
public enum DiceValue {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    private final int value;

    DiceValue(int value) {
        this.value = value;
    }

    public static DiceValue getRandomValue() {
        DiceValue[] values = DiceValue.values();
        int randomIndex = ThreadLocalRandom.current().nextInt(values.length);
        return values[randomIndex];
    }
    
}
