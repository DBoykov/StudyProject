package liga.study.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class Controller {

    @GetMapping
    public ResponseEntity<String> response(@RequestHeader(name = "custom-token") String token) {
        return ResponseEntity.ok(token);
    }

}
