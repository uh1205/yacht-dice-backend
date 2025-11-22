package yacht.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yacht.dto.join.JoinRequest;
import yacht.dto.join.JoinResponse;
import yacht.service.PlayerService;

@RestController
@RequestMapping("/join")
@RequiredArgsConstructor
public class JoinController {

    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<JoinResponse> join(@RequestBody JoinRequest request) {
        JoinResponse response = playerService.register(request.nickname());
        return ResponseEntity.ok().body(response);
    }

}
