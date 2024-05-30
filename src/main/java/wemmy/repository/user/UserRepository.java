package wemmy.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import wemmy.domain.user.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(Long id);             // 회원 기본키로 조회
    Optional<UserEntity> findByEmail(String email);     // 회원 이메일로 조회

    Optional<UserEntity> findByRefreshToken(String refreshToken);  // refreshToken과 일치한는 사용자 조회
}
