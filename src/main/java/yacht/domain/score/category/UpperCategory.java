package yacht.domain.score.category;

import yacht.domain.dice.DiceSet;
import yacht.domain.dice.DiceValue;
import yacht.domain.score.Score;

public class UpperCategory implements Category {

    private final DiceValue diceValue;

    public UpperCategory(DiceValue diceValue) {
        this.diceValue = diceValue;
    }

    @Override
    public Score calculateScore(DiceSet diceSet) {
        int count = diceSet.countOf(diceValue);
        return Score.of(count * diceValue.getValue());
    }

    @Override
    public boolean isUpperCategory() {
        return true;
    }

}
