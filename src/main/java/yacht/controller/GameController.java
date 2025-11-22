package yacht.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yacht.dto.game.DiceActionRequest;
import yacht.dto.game.GameStateResponse;
import yacht.dto.game.PlayerActionRequest;
import yacht.dto.game.ScoreRecordRequest;
import yacht.service.GameService;

@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/game/{roomId}/state")
    public ResponseEntity<GameStateResponse> getCurrentState(@PathVariable String roomId) {
        GameStateResponse response = gameService.getCurrentState(roomId);
        return ResponseEntity.ok(response);
    }

    @MessageMapping("/game/{roomId}/state")
    public void getGameState(@DestinationVariable String roomId) {
        GameStateResponse response = gameService.getCurrentState(roomId);
        messagingTemplate.convertAndSend("/topic/room/" + roomId, response);
    }

    @MessageMapping("/game/{roomId}/lock-dice")
    public void lockDice(@DestinationVariable String roomId, DiceActionRequest request) {
        GameStateResponse response = gameService.lockDice(roomId, request);
        broadcast(roomId, response);
    }

    @MessageMapping("/game/{roomId}/unlock-dice")
    public void unlockDice(@DestinationVariable String roomId, DiceActionRequest request) {
        GameStateResponse response = gameService.unlockDice(roomId, request);
        broadcast(roomId, response);
    }

    @MessageMapping("/game/{roomId}/roll-dice")
    public void rollDice(@DestinationVariable String roomId, PlayerActionRequest request) {
        GameStateResponse response = gameService.rollDice(roomId, request);
        broadcast(roomId, response);
    }

    @MessageMapping("/game/{roomId}/record-score")
    public void recordScore(@DestinationVariable String roomId, ScoreRecordRequest request) {
        GameStateResponse response = gameService.recordScore(roomId, request);
        broadcast(roomId, response);
    }

    private void broadcast(String roomId, GameStateResponse gameStateResponse) {
        messagingTemplate.convertAndSend(
                "/topic/room/" + roomId,
                gameStateResponse
        );
    }

}
