package yacht.domain.dice.strategy;

import java.util.Random;

public class JavaRandomStrategy implements DiceRollStrategy {

    private final Random random = new Random();

    @Override
    public int roll(int minValue, int maxValue) {
        return random.nextInt(maxValue) + minValue;
    }

}
