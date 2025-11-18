package yacht.domain.score.category;

import yacht.domain.dice.Dice.DiceValue;

public enum CategoryType {

    ACES(new UpperCategory(new DiceValue(1))),
    DEUCES(new UpperCategory(new DiceValue(2))),
    THREES(new UpperCategory(new DiceValue(3))),
    FOURS(new UpperCategory(new DiceValue(4))),
    FIVES(new UpperCategory(new DiceValue(5))),
    SIXES(new UpperCategory(new DiceValue(6))),

    CHOICE(new Choice()),
    FOUR_OF_A_KIND(new FourOfAKind()),
    FULL_HOUSE(new FullHouse()),
    SMALL_STRAIGHT(new SmallStraight()),
    LARGE_STRAIGHT(new LargeStraight()),
    YACHT(new Yacht()),
    ;

    private final Category category;

    CategoryType(Category category) {
        this.category = category;
    }

    public boolean isUpper() {
        return category instanceof UpperCategory;
    }

}
