package yacht.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yacht.assembler.GameStateAssembler;
import yacht.domain.game.GameRoom;
import yacht.domain.game.Player;
import yacht.domain.score.category.ScoreCategory;
import yacht.dto.game.DiceActionRequest;
import yacht.dto.game.GameStateResponse;
import yacht.dto.game.PlayerActionRequest;
import yacht.dto.game.ScoreRecordRequest;
import yacht.repository.PlayerRepository;
import yacht.repository.RoomRepository;

@Service
@RequiredArgsConstructor
public class GameService {

    private final RoomRepository roomRepository;
    private final PlayerRepository playerRepository;
    private final GameStateAssembler stateAssembler;

    public GameStateResponse getCurrentState(String roomId) {
        GameRoom room = findRoom(roomId);
        return stateAssembler.toResponse(room);
    }

    public GameStateResponse lockDice(String roomId, DiceActionRequest request) {
        GameRoom room = findRoom(roomId);
        Player player = findPlayer(request.getPlayerId());
        room.assertPlayerTurn(player);
        room.lockDice(request.getDiceIndex());
        return stateAssembler.toResponse(room);
    }

    public GameStateResponse unlockDice(String roomId, DiceActionRequest request) {
        GameRoom room = findRoom(roomId);
        Player player = findPlayer(request.getPlayerId());
        room.assertPlayerTurn(player);
        room.unlockDice(request.getDiceIndex());
        return stateAssembler.toResponse(room);
    }

    public GameStateResponse rollDice(String roomId, PlayerActionRequest request) {
        GameRoom room = findRoom(roomId);
        Player player = findPlayer(request.getPlayerId());
        room.assertPlayerTurn(player);
        room.rollDice();
        return stateAssembler.toResponse(room);
    }

    public GameStateResponse recordScore(String roomId, ScoreRecordRequest request) {
        GameRoom room = findRoom(roomId);
        Player player = findPlayer(request.getPlayerId());
        room.assertPlayerTurn(player);
        ScoreCategory category = ScoreCategory.fromName(request.getCategoryName());
        room.recordScore(category);
        return stateAssembler.toResponse(room);
    }

    private GameRoom findRoom(String roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 게임 방입니다: " + roomId));
    }

    private Player findPlayer(String playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 플레이어입니다: " + playerId));

    }

}
