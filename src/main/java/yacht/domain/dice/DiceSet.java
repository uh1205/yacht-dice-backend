package yacht.domain.dice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import yacht.domain.dice.Dice.DiceValue;
import yacht.domain.dice.strategy.DiceRollStrategy;

public class DiceSet {

    public static final int DICE_COUNT = 5;

    private final List<Dice> dices = new ArrayList<>();
    private final Map<DiceValue, Integer> diceCounts = new HashMap<>();

    private boolean isRolled = false;

    public DiceSet(DiceRollStrategy rollStrategy) {
        IntStream.range(0, DICE_COUNT)
                .mapToObj(i -> new Dice(rollStrategy))
                .forEach(dices::add);
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

    public void roll() {
        dices.forEach(Dice::roll);
        isRolled = true;
        countAll();
    }

    private void countAll() {
        diceCounts.clear();
        dices.stream()
                .map(Dice::getValue)
                .forEach(value ->
                        diceCounts.put(value, countOf(value) + 1));
    }

    public int countOf(DiceValue value) {
        return diceCounts.getOrDefault(value, 0);
    }

    public int sum() {
        ensureRolled();
        return dices.stream()
                .map(Dice::getValue)
                .mapToInt(DiceValue::value)
                .sum();
    }

    public boolean hasSameDiceMoreThan(int amount) {
        ensureRolled();
        return diceCounts.values().stream()
                .anyMatch(count -> count >= amount);
    }

    public boolean isFullHouse() {
        ensureRolled();
        return diceCounts.containsValue(2) && diceCounts.containsValue(3);
    }

    public boolean hasStraight(int length) {
        ensureRolled();
        int continuous = 0;
        for (int value = DiceValue.MIN_VALUE; value <= DiceValue.MAX_VALUE; value++) {
            if (countOf(new DiceValue(value)) == 0) {
                continuous = 0;
                continue;
            }
            continuous++;
            if (continuous >= length) {
                return true;
            }
        }
        return false;
    }

    public boolean isYacht() {
        ensureRolled();
        return diceCounts.containsValue(DICE_COUNT);
    }

    private void ensureRolled() {
        if (!isRolled) {
            throw new IllegalStateException("주사위를 아직 굴리지 않았습니다.");
        }
    }

    public void reset() {
        dices.forEach(Dice::reset);
        diceCounts.clear();
        isRolled = false;
    }

}
