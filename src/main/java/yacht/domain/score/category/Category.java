package yacht.domain.score.category;

import yacht.domain.dice.DiceSet;
import yacht.domain.score.Score;

public interface Category {

    Score calculateScore(DiceSet diceSet);

    boolean isUpperCategory();

}
