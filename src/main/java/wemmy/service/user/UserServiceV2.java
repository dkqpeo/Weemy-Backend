package wemmy.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wemmy.domain.area.district.SiggAreas;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.user.constant.Role;
import wemmy.domain.user.constant.UserType;
import wemmy.dto.user.LoginDTO;
import wemmy.dto.user.UserRegisterDTOV2;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.ControllerException;
import wemmy.global.config.error.exception.MemberException;
import wemmy.global.token.jwt.TokenProvider;
import wemmy.global.token.jwt.dto.TokenDto;
import wemmy.repository.user.UserRepositoryV2;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceV2 {

    private final UserRepositoryV2 userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserEntityV2 signUp(UserEntityV2 user) {
        validateEmail(user.getEmail());

        return userRepository.save(user);
    }

    public UserEntityV2 signUpByAdmin(String email, String password) {
        validateEmail(email);

        UserEntityV2 user = UserEntityV2.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .userType(UserType.LOCAL)
                .createdAt(LocalDateTime.now())
                .role(Role.ADMIN)
                .build();

        return userRepository.save(user);
    }

    public LoginDTO.loginResponse login(String email, String password) {

        UserEntityV2 user = finBydUserEmail(email)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_EXISTS_EMAIL));

        String dbPw = user.getPassword();

        if(!passwordEncoder.matches(password, dbPw))
            throw new MemberException(ErrorCode.NOT_EQUAL_PASSWORD);

        TokenDto token = tokenProvider.createToken(user.getId(), email, String.valueOf(user.getRole()));
        user.updateToken(token);

        return LoginDTO.loginResponse.of(token);
    }

    public boolean updatePassword(Long id, String oldPasswoed, String newPassword) {

        UserEntityV2 user = findByUserId(id);

        if(!passwordEncoder.matches(oldPasswoed, user.getPassword()))
            throw new MemberException(ErrorCode.NOT_EQUAL_PASSWORD);

        user.updatePassword(newPassword);
        return true;
    }

    public boolean updateArea(Long id, SiggAreas area) {
        UserEntityV2 user = findByUserId(id);

        user.updateArea(area);
        return true;
    }

    public boolean updateUserInfo(Long id, UserRegisterDTOV2 dto) {
        UserEntityV2 user = findByUserId(id);

        user.updateUserInfo(dto);
        return true;
    }

    public void validateEmail(String email) {
        Optional<UserEntityV2> userEntity = userRepository.findByEmail(email);

        if(userEntity.isPresent())
            throw new MemberException(ErrorCode.ALREADY_REGISTERED_MEMBER);
    }

    public UserEntityV2 findByUserId(Long id) {
        UserEntityV2 user = userRepository.findById(id)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_EXISTS_MEMBER));
        return user;
    }

    public Optional<UserEntityV2> finBydUserEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntityV2 validateAdmin(String adminId) {
        UserEntityV2 admin = userRepository.findByEmail(adminId)
                .orElseThrow(() -> new ControllerException(ErrorCode.USER_NOT_EXISTS));
        if(admin.getRole() != Role.ADMIN)
           throw  new ControllerException(ErrorCode.NOT_ADMIN_USER);

        return admin;
    }

    public Optional<UserEntityV2> findUserByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken);
    }
}
