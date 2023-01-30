package liga.study.controller;

import liga.study.dto.Credentials;
import liga.study.dto.JwtToken;
import liga.study.service.user.UserLoginService;
import liga.study.service.user.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final UserRegistrationService userRegistrationService;

    @Autowired
    private final UserLoginService userLoginService;


    @PostMapping("/registration")
    @Transactional
    public ResponseEntity<JwtToken> registration(@RequestBody Credentials userCredentials) {
        return ResponseEntity.ok(userRegistrationService.createUser(userCredentials));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody Credentials userCredentials) {
        return ResponseEntity.ok(userLoginService.login(userCredentials));
    }


}
