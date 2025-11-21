package yacht.domain.score.category;

import java.util.Arrays;
import lombok.Getter;
import yacht.domain.dice.DiceSet;
import yacht.domain.dice.DiceValue;
import yacht.domain.score.Score;

@Getter
public enum ScoreCategory {

    ACES("에이스", new UpperCategory(DiceValue.ONE)),
    DEUCES("듀스", new UpperCategory(DiceValue.TWO)),
    THREES("트레이", new UpperCategory(DiceValue.THREE)),
    FOURS("포", new UpperCategory(DiceValue.FOUR)),
    FIVES("파이브", new UpperCategory(DiceValue.FIVE)),
    SIXES("식스", new UpperCategory(DiceValue.SIX)),

    CHOICE("초이스", new Choice()),
    FOUR_OF_A_KIND("포 다이스", new FourOfAKind()),
    FULL_HOUSE("풀 하우스", new FullHouse()),
    SMALL_STRAIGHT("S. 스트레이트", new SmallStraight()),
    LARGE_STRAIGHT("L. 스트레이트", new LargeStraight()),
    YACHT("요트", new Yacht());

    private final String categoryName;
    private final Category category;

    ScoreCategory(String categoryName, Category category) {
        this.categoryName = categoryName;
        this.category = category;
    }

    public static ScoreCategory fromName(String categoryName) {
        return Arrays.stream(values())
                .filter(c -> c.name().equalsIgnoreCase(categoryName)
                        || c.categoryName.equals(categoryName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카테고리입니다: " + categoryName));
    }

    public boolean isUpperCategory() {
        return category.isUpperCategory();
    }

    public boolean isLowerCategory() {
        return !category.isUpperCategory();
    }

    public Score calculateScore(DiceSet diceSet) {
        return category.calculateScore(diceSet);
    }

}
