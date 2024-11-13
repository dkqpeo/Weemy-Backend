package wemmy.domain.welfare;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import wemmy.domain.area.Regions;
import wemmy.domain.common.BaseTimeEntity;
import wemmy.domain.user.UserEntity;
import wemmy.domain.user.UserEntityV2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "program_registration")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramRegistration{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_registration_id")
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
    @JoinColumn(name = "program", referencedColumnName = "program_id")
    Program program;                   // 프로그램

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user", referencedColumnName = "user_id")
    UserEntityV2 user;                 // 신청자 정보(이름, 연락처, 이메일)

    @Column(name = "create_time")
    private LocalDateTime createTime;

}
