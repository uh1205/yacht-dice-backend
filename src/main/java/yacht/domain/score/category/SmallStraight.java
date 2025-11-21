package yacht.domain.score.category;

import yacht.domain.dice.DiceSet;
import yacht.domain.score.Score;

public class SmallStraight implements Category {

    public static final int LENGTH = 4;
    public static final int SCORE = 15;

    @Override
    public Score calculateScore(DiceSet diceSet) {
        if (diceSet.hasStraight(LENGTH)) {
            return Score.of(SCORE);
        }
        return Score.ofZero();
    }

    @Override
    public boolean isUpperCategory() {
        return false;
    }

}
