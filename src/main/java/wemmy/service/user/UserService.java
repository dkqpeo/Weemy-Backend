package wemmy.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.area.district.SiggAreas;
import wemmy.domain.user.UserEntity;
import wemmy.dto.user.LoginDTO;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.MemberException;
import wemmy.global.token.jwt.TokenProvider;
import wemmy.global.token.jwt.dto.TokenDto;
import wemmy.repository.user.UserRepository;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserEntity signUp(UserEntity user) {
        Optional<UserEntity> findUser = finBydUserEmail(user.getEmail());
        if (findUser.isPresent())
            throw new MemberException(ErrorCode.ALREADY_REGISTERED_MEMBER);

        return userRepository.save(user);
    }

    public LoginDTO.response login(String email, String password) {

        UserEntity user = finBydUserEmail(email)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_EXISTS_EMAIL));

        String dbPw = user.getPassword();

        if(!passwordEncoder.matches(password, dbPw))
            throw new MemberException(ErrorCode.NOT_EQUAL_PASSWORD);

        TokenDto token = tokenProvider.createToken(user.getId(), email);
        user.updateToken(token);

        return LoginDTO.response.of(token);
    }

    public boolean updatePassword(Long id, String oldPasswoed, String newPassword) {

        UserEntity user = findByUserId(id);

        if(!passwordEncoder.matches(oldPasswoed, user.getPassword()))
            throw new MemberException(ErrorCode.NOT_EQUAL_PASSWORD);

        user.updatePassword(newPassword);
        return true;
    }

    public boolean updateArea(Long id, SiggAreas area) {
        UserEntity user = findByUserId(id);

        user.updateArea(area);
        return true;
    }

    public void validateEmail(String email) {
        Optional<UserEntity> userEntity = finBydUserEmail(email);

        if(userEntity.isPresent())
            throw new MemberException(ErrorCode.ALREADY_REGISTERED_MEMBER);
    }

    public UserEntity findByUserId(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_EXISTS_MEMBER));
        return user;
    }

    public Optional<UserEntity> finBydUserEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserEntity> findUserByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken);
    }
}
