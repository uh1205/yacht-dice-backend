package yacht.domain.dice;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiceSetTest {

    @ParameterizedTest
    @ValueSource(ints = {-2, -1, 5, 6})
    void 주사위_인덱스_범위를_벗어나면_예외_발생(int index) {
        // given
        DiceSet diceSet = new DiceSet();

        // when & then
        assertThatThrownBy(() -> diceSet.lock(index))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주사위_인덱스_범위_검증(int index) {
        // given
        DiceSet diceSet = new DiceSet();

        // when
        diceSet.lock(index);

        // then

    }

}
