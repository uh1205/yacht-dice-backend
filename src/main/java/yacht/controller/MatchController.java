package yacht.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yacht.dto.match.MatchRequest;
import yacht.service.MatchService;

@RestController
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody MatchRequest request) {
        matchService.joinMatch(request.playerId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancel(@RequestBody MatchRequest request) {
        matchService.cancelMatch(request.playerId());
        return ResponseEntity.ok().build();
    }

}
