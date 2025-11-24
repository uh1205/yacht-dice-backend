package yacht.domain.dice;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class DiceSet {

    public static final int DICE_COUNT = 5;

    private final List<Dice> dices = new ArrayList<>();
    private final Map<DiceValue, Integer> diceCounts = new EnumMap<>(DiceValue.class);

    public DiceSet() {
        IntStream.range(0, DICE_COUNT)
                .mapToObj(i -> new Dice())
                .forEach(dices::add);
    }

    /**
     * 특정 주사위를 굴리지 않도록 고정합니다.
     *
     * @param diceIndex 고정할 주사위의 인덱스
     */
    public void lock(int diceIndex) {
        validateDiceIndex(diceIndex);
        dices.get(diceIndex).lock();
    }

    /**
     * 고정한 주사위를 다시 굴리도록 고정 해제합니다.
     *
     * @param diceIndex 고정 해제할 주사위의 인덱스
     */
    public void unlock(int diceIndex) {
        validateDiceIndex(diceIndex);
        dices.get(diceIndex).unlock();
    }

    private void validateDiceIndex(int index) {
        if (index < 0 || index >= DICE_COUNT) {
            throw new IllegalArgumentException("잘못된 주사위 인덱스입니다.");
        }
    }

    /**
     * 주사위를 굴립니다. (고정된 주사위들만 굴려집니다.)
     */
    public void roll() {
        dices.forEach(Dice::roll);
        countDiceValues();
    }

    private void countDiceValues() {
        diceCounts.clear();
        dices.stream()
                .map(Dice::getValue)
                .forEach(value -> diceCounts.put(value, countOf(value) + 1));
    }

    /**
     * 모든 주사위의 고정 여부를 초기화합니다.
     */
    public void reset() {
        dices.forEach(Dice::reset);
    }

    /**
     * 동일한 주사위 눈이 특정 개수 이상 존재하는지 확인합니다.
     *
     * @param amount 특정 개수
     * @return 특정 개수 이상 존재 여부
     */
    public boolean hasSameDiceMoreThan(int amount) {
        return diceCounts.values().stream()
                .anyMatch(count -> count >= amount);
    }

    /**
     * 주사위 결과가 풀 하우스인지 확인합니다.
     *
     * @return 풀 하우스 여부
     */
    public boolean isFullHouse() {
        return diceCounts.containsValue(2) && diceCounts.containsValue(3);
    }

    /**
     * 주사위 결과가 특정 길이 이상의 스트레이트인지 확인합니다.
     *
     * @param length 스트레이트 길이
     * @return 특정 길이 이상 스트레이트 여부
     */
    public boolean hasStraight(int length) {
        int continuous = 0;
        for (DiceValue value : DiceValue.values()) {
            if (countOf(value) == 0) {
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

    /**
     * 주사위 결과가 요트인지 확인합니다.
     *
     * @return 요트 여부
     */
    public boolean isYacht() {
        return diceCounts.containsValue(DICE_COUNT);
    }

    /**
     * 특정 주사위 눈의 개수를 반환합니다.
     *
     * @param value 주사위 눈
     * @return 주사위 눈의 개수
     */
    public int countOf(DiceValue value) {
        return diceCounts.getOrDefault(value, 0);
    }

    /**
     * 모든 주사위의 눈을 더한 값을 반환합니다.
     *
     * @return 모든 주사위 눈의 총합
     */
    public int sum() {
        return dices.stream()
                .map(Dice::getValue)
                .mapToInt(DiceValue::getValue)
                .sum();
    }

    /**
     * 모든 주사위의 고정 여부를 반환합니다.
     *
     * @return 모든 주사위 고정 여부
     */
    public List<Boolean> getDiceLocks() {
        return dices.stream()
                .map(Dice::isLocked)
                .toList();
    }

    /**
     * 모든 주사위의 눈을 반환합니다.
     *
     * @return 모든 주사위 눈
     */
    public List<DiceValue> getDiceValues() {
        return dices.stream()
                .map(Dice::getValue)
                .toList();
    }

}
