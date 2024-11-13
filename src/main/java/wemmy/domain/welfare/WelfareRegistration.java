package wemmy.domain.welfare;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wemmy.domain.common.BaseTimeEntity;
import wemmy.domain.user.UserEntityV2;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "welfare_registration")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WelfareRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "welfare_registration_id")
    private Long id;

    @Column
    private String address;

    @Column
    private String addressDetail;

    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    @Column
    private LocalDate birthday;

    @Column
    private String phone;

    @Column
    private String name;

    @Column
    private String email;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "welfare", referencedColumnName = "welfare_id")
    Welfare welfare;                   // 복지정보.

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user", referencedColumnName = "user_id")
    UserEntityV2 user;                 // 신청자 정보(이름, 연락처, 이메일)

    @Column(name = "create_time")
    private LocalDateTime createTime;
}
