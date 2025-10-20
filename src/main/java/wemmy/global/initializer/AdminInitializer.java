package wemmy.global.initializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.user.constant.Role;
import wemmy.domain.user.constant.UserType;
import wemmy.repository.user.UserRepositoryV2;

import java.time.LocalDateTime;

/**
 * 애플리케이션 시작 시 관리자 계정을 자동으로 생성합니다.
 * 이미 관리자 계정이 존재하면 생성하지 않습니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepositoryV2 userRepository;
    private final PasswordEncoder passwordEncoder;

    // 기본 관리자 정보 (필요시 application.yml에서 설정값으로 변경 가능)
    private static final String ADMIN_EMAIL = "teamWemmy@gmail.com";
    private static final String ADMIN_PASSWORD = "admin1234";
    private static final String ADMIN_NAME = "관리자";

    @Override
    public void run(String... args) {
        try {
            // 이미 관리자 계정이 존재하는지 확인
            if (userRepository.findByEmail(ADMIN_EMAIL).isPresent()) {
                log.info("관리자 계정이 이미 존재합니다: {}", ADMIN_EMAIL);
                return;
            }

            // 관리자 계정 생성
            UserEntityV2 admin = UserEntityV2.builder()
                    .email(ADMIN_EMAIL)
                    .password(passwordEncoder.encode(ADMIN_PASSWORD))
                    .name(ADMIN_NAME)
                    .userType(UserType.LOCAL)
                    .role(Role.ADMIN)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(admin);
            log.info("관리자 계정이 생성되었습니다: {}", ADMIN_EMAIL);
            log.info("초기 비밀번호: {}", ADMIN_PASSWORD);
            log.warn("⚠️ 보안을 위해 초기 비밀번호를 반드시 변경하세요!");

        } catch (Exception e) {
            log.error("관리자 계정 생성 중 오류가 발생했습니다.", e);
        }
    }
}
