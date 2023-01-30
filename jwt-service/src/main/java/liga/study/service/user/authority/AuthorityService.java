package liga.study.service.user.authority;

import liga.study.domain.Authority;
import liga.study.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static liga.study.constants.Constants.USER_ROLE;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    @Autowired
    private final AuthorityRepository repository;

    @Transactional
    public Authority getUserRole(){
        return repository.getAuthorityByName(USER_ROLE).orElse(createUserRole());
    }

    private Authority createUserRole(){
        Authority authority = new Authority();
        authority.setName(USER_ROLE);
        return repository.save(authority);
    }

}
