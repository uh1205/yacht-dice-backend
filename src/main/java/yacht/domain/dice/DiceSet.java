package yacht.domain.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import yacht.domain.dice.strategy.DiceRollStrategy;

public class DiceSet {

    public static final int DICE_COUNT = 5;

    private final List<Dice> dices = new ArrayList<>();

    public DiceSet(DiceRollStrategy rollStrategy) {
        IntStream.range(0, DICE_COUNT)
                .mapToObj(i -> new Dice(rollStrategy))
                .forEach(dices::add);
    }

    public void roll() {
        dices.forEach(Dice::roll);
    }

    public void lock(int index) {
        validateIndex(index);
        dices.get(index).lock();
    }

    public void unlock(int index) {
        validateIndex(index);
        dices.get(index).unlock();
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= DICE_COUNT) {
            throw new IllegalArgumentException("잘못된 주사위 인덱스입니다.");
        }
    }

}
