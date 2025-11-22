package yacht.repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
import yacht.domain.game.GameRoom;

@Repository
public class RoomRepository {

    private final Map<String, GameRoom> rooms = new ConcurrentHashMap<>();

    public GameRoom create() {
        String roomId = createRoomId();
        GameRoom room = new GameRoom(roomId);
        rooms.put(roomId, room);
        return room;
    }

    private String createRoomId() {
        return "room-" + UUID.randomUUID();
    }

    public Optional<GameRoom> findById(String roomId) {
        return Optional.ofNullable(rooms.get(roomId));
    }

}
