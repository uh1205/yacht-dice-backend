package yacht.domain.score.category;

import yacht.domain.dice.DiceSet;
import yacht.domain.score.Score;

public class Yacht implements Category {

    public static final int SCORE = 50;

    @Override
    public Score calculateScore(DiceSet diceSet) {
        if (diceSet.isYacht()) {
            return Score.of(SCORE);
        }
        return Score.ofZero();
    }

}
