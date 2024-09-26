package wemmy.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import wemmy.domain.user.UserEntityV2;

import java.util.Optional;

@Repository
public interface UserRepositoryV2 extends JpaRepository<UserEntityV2, Long> {

    Optional<UserEntityV2> findById(Long id);             // 회원 기본키로 조회
    Optional<UserEntityV2> findByEmail(String email);     // 회원 이메일로 조회
    Optional<UserEntityV2> findByRefreshToken(String refreshToken);  // refreshToken과 일치한는 사용자 조회
}
