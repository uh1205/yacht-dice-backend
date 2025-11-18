package yacht.domain.score.category;

import yacht.domain.dice.DiceSet;
import yacht.domain.score.Score;

public class Choice implements Category {

    @Override
    public Score calculateScore(DiceSet diceSet) {
        return Score.of(diceSet.sum());
    }

}
