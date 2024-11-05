package wemmy.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wemmy.domain.area.district.SiggAreas;
import wemmy.domain.user.constant.Role;
import wemmy.domain.user.constant.UserType;
import wemmy.dto.user.UserRegisterDTOV2;
import wemmy.global.converter.StringListConverter;
import wemmy.global.token.jwt.dto.TokenDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Entity (name = "USERV2")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;                    // 기본키

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserType userType;          // 회원 구분 (로컬, 카카오 등)

    @Column
    private String name;

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
    private Role role;                  // ADMIN, USER

    @Column(length = 250)
    private String refreshToken;

    // -- 온보딩 시 수집 내용
    @ManyToOne
    @JoinColumn(name = "sigg_id", referencedColumnName = "sigg_id")
    SiggAreas sigg_id;

    /*@Convert(converter = StringListConverter.class)
    private List<String> topic;*/         // 관심 주제 (임신 준비, 임신, 출산, 양육)

    @Column(length = 15)
    private String gender;

    @Convert(converter = StringListConverter.class)
    private List<String> userState;     // 결혼, 임신, 출산

    @Convert(converter = StringListConverter.class)
    private List<String> babyState;     // 자녀 상태 ["영유아", "어린이집", "유치원", "성인", "초등학생", "중학생", "고등학생"]

    @Convert(converter = StringListConverter.class)
    private List<String> characteristic;    // 대상특성 (해당 없음, 다문화 가정 등..)

    @Column
    private int familyCount;        // 가구원 수

    @Column
    private int monthlyImcome;      // 가구 월소득
    // ---

    public void updateToken(TokenDto tokenDto) {
        this.refreshToken = tokenDto.getRefreshToken();
    }
    public void updatePassword(String password){
        this.password = password;
    }
    public void updateArea(SiggAreas area) {this.sigg_id = area;}

    public void updateUserInfo(UserRegisterDTOV2 dto) {
        //this.topic = dto.getTopic();
        this.gender = dto.getGender();
        this.userState = dto.getUserState();
        this.characteristic = dto.getCharacteristic();
        this.familyCount = dto.getFamilyCount();
        this.monthlyImcome = dto.getMonthlyImcome();

        if(dto.getBaby() == true)
            this.babyState = dto.getBabyState();
        else
            this.babyState = Collections.singletonList("false");
    }
}