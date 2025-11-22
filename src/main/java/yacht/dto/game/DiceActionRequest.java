package yacht.dto.game;

import lombok.Data;

@Data
public class DiceActionRequest {

    private String playerId;
    private int diceIndex;

}
