package yacht.service;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import yacht.domain.game.GameRoom;
import yacht.domain.game.Player;
import yacht.dto.GameMessageType;
import yacht.repository.PlayerRepository;
import yacht.repository.RoomRepository;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final Queue<Player> waitingQueue = new ConcurrentLinkedQueue<>();
    private final RoomRepository roomRepository;
    private final PlayerRepository playerRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void joinMatch(String playerId) {
        Player player = findPlayer(playerId);
        addPlayerInQueue(player);
        matchIfPossible();
    }

    private void addPlayerInQueue(Player player) {
        if (waitingQueue.contains(player)) {
            throw new IllegalStateException("이미 대기 중인 플레이어입니다.");
        }
        waitingQueue.add(player);
    }

    private void matchIfPossible() {
        if (waitingQueue.size() >= 2) {
            createNewGameRoom();
        }
    }

    private void createNewGameRoom() {
        Player player1 = waitingQueue.poll();
        Player player2 = waitingQueue.poll();

        GameRoom room = roomRepository.create();
        room.addPlayer(player1);
        room.addPlayer(player2);

        // 각 플레이어에게 매칭 성공 알림
        room.getPlayers().stream()
                .map(Player::getPlayerId)
                .forEach(playerId -> messagingTemplate.convertAndSend(
                        "/topic/player/" + playerId,
                        Map.of(
                                "type", GameMessageType.MATCH_SUCCESS.name(),
                                "roomId", room.getRoomId()
                        )
                ));
    }

    public void cancelMatch(String playerId) {
        Player player = findPlayer(playerId);
        waitingQueue.remove(player);
    }

    private Player findPlayer(String playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 플레이어입니다: " + playerId));

    }

}
