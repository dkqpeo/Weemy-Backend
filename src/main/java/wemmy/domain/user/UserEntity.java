package wemmy.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wemmy.domain.area.district.SiggAreas;
import wemmy.domain.user.constant.Role;
import wemmy.domain.user.constant.UserType;
import wemmy.global.token.jwt.dto.TokenDto;
import java.time.LocalDateTime;


@Entity (name = "USER")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserType userType;          // 회원 구분 (로컬, 카카오 등)

    @Column(unique = true, length = 50, nullable = false)
    private String email;               // 로그인 시 사용되는 이메일

    @Column(length = 200)
    private String password;

    @Column(length = 13)
    private String phone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;             // 최초 회원가입 날짜

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(length = 250)
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "sigg_id", referencedColumnName = "sigg_id")
    SiggAreas sigg_id;

    public void updateToken(TokenDto tokenDto) {
        this.refreshToken = tokenDto.getRefreshToken();
    }

    public void updatePassword(String password){
        this.password = password;
    }
    public void updateArea(SiggAreas area) {this.sigg_id = area;}
}