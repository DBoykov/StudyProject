package liga.study.service.user;

import liga.study.config.security.JwtUtil;
import liga.study.domain.UserInfo;
import liga.study.dto.Credentials;
import liga.study.dto.JwtToken;
import liga.study.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    @Autowired
    private final UserInfoRepository userInfoRepository;

    @Autowired
    private final AuthenticationManager authManager;

    @Autowired
    private final JwtUtil jwtUtil;

    @Transactional
    public JwtToken login(Credentials credentials) {

        if (Objects.isNull(credentials.getPassword()) || Objects.isNull(credentials.getUsername())) {
            throw new IllegalArgumentException();
        }

        try {
            UserInfo foundedUser = userInfoRepository.findByUsername(credentials.getUsername()).orElseThrow(
                    () -> new UsernameNotFoundException(String.format("User not found: %s", credentials.getUsername()))
            );
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(foundedUser.getUsername(), credentials.getPassword(),
                            foundedUser.getAuthorities());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(credentials.getUsername());

            return new JwtToken(token);
        } catch (AuthenticationException authExc) {
            throw new RuntimeException("Invalid Login Credentials");
        }
    }


}
