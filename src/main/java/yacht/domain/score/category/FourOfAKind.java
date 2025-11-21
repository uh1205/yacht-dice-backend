package yacht.domain.score.category;

import yacht.domain.dice.DiceSet;
import yacht.domain.score.Score;

public class FourOfAKind implements Category {

    @Override
    public Score calculateScore(DiceSet diceSet) {
        if (diceSet.hasSameDiceMoreThan(4)) {
            return Score.of(diceSet.sum());
        }
        return Score.ofZero();
    }

    @Override
    public boolean isUpperCategory() {
        return false;
    }

}
