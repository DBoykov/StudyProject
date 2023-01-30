package liga.study.service.user;

import liga.study.config.security.JwtUtil;
import liga.study.domain.Authority;
import liga.study.domain.UserInfo;
import liga.study.dto.Credentials;
import liga.study.dto.JwtToken;
import liga.study.repository.UserInfoRepository;
import liga.study.service.user.authority.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    @Autowired
    private final UserInfoRepository userInfoRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthorityService authorityService;

    @Autowired
    private final JwtUtil jwtUtil;

    @Transactional
    public JwtToken createUser(Credentials credentials) {

        if (Objects.isNull(credentials.getPassword()) || Objects.isNull(credentials.getUsername())) {
            throw new IllegalArgumentException();
        }

        UserInfo userInfo = userInfoRepository.findByUsername(credentials.getUsername()).orElse(
            newUser(credentials)
        );

        UserInfo savedUser = userInfoRepository.save(userInfo);
        return new JwtToken(jwtUtil.generateToken(savedUser.getUsername()));
    }

    private UserInfo newUser(Credentials credentials) {
        Authority userRole = authorityService.getUserRole();
        return UserInfo.builder()
                .username(credentials.getUsername())
                .hashedPassword(passwordEncoder.encode(credentials.getPassword()))
                .authorities(Collections.singleton(userRole))
                .build();
    }

}
