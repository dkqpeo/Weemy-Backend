package wemmy.service.welfare;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.user.constant.Role;
import wemmy.domain.user.constant.UserType;
import wemmy.domain.welfare.Welfare;
import wemmy.domain.welfare.WelfareRegistration;
import wemmy.repository.welfare.WelfareRegistrationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BenefitRegistrationServiceTest {

    @Mock
    private WelfareRegistrationRepository welfareRegistrationRepository;

    @InjectMocks
    private BenefitRegistrationService benefitRegistrationService;

    private UserEntityV2 user;
    private Welfare welfare;
    private WelfareRegistration welfareRegistration;

    @BeforeEach
    void setUp() {
        user = UserEntityV2.builder()
                .id(1L)
                .email("test@example.com")
                .password("password123")
                .name("홍길동")
                .userType(UserType.LOCAL)
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();

        welfare = Welfare.builder()
                .id(1L)
                .title("출산 지원금")
                .content("출산 가정 지원")
                .build();

        welfareRegistration = WelfareRegistration.builder()
                .id(1L)
                .name("홍길동")
                .birthday(LocalDate.of(1990, 1, 1))
                .address("서울시 강남구")
                .addressDetail("테헤란로 123")
                .phone("010-1234-5678")
                .email("test@example.com")
                .welfare(welfare)
                .user(user)
                .createTime(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("복지 등록 저장 성공")
    void register_Success() {
        // given
        when(welfareRegistrationRepository.save(any(WelfareRegistration.class)))
                .thenReturn(welfareRegistration);

        // when
        benefitRegistrationService.register(welfareRegistration);

        // then
        verify(welfareRegistrationRepository, times(1)).save(welfareRegistration);
    }

    @Test
    @DisplayName("전체 복지 등록 조회 성공")
    void findAll_Success() {
        // given
        List<WelfareRegistration> registrations = List.of(welfareRegistration);
        when(welfareRegistrationRepository.findAll()).thenReturn(registrations);

        // when
        List<WelfareRegistration> result = benefitRegistrationService.findAll();

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("홍길동");
        assertThat(result.get(0).getWelfare().getTitle()).isEqualTo("출산 지원금");

        verify(welfareRegistrationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("전체 복지 등록 조회 - 빈 리스트")
    void findAll_EmptyList() {
        // given
        when(welfareRegistrationRepository.findAll()).thenReturn(new ArrayList<>());

        // when
        List<WelfareRegistration> result = benefitRegistrationService.findAll();

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(welfareRegistrationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("복지 중복 검증 - 등록 이력 존재")
    void validateBenefit_Exists() {
        // given
        when(welfareRegistrationRepository.findByWelfareAndUser(welfare, user))
                .thenReturn(Optional.of(welfareRegistration));

        // when
        Optional<WelfareRegistration> result = benefitRegistrationService.validateBenefit(welfare, user);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getWelfare()).isEqualTo(welfare);
        assertThat(result.get().getUser()).isEqualTo(user);

        verify(welfareRegistrationRepository, times(1)).findByWelfareAndUser(welfare, user);
    }

    @Test
    @DisplayName("복지 중복 검증 - 등록 이력 없음")
    void validateBenefit_NotExists() {
        // given
        when(welfareRegistrationRepository.findByWelfareAndUser(welfare, user))
                .thenReturn(Optional.empty());

        // when
        Optional<WelfareRegistration> result = benefitRegistrationService.validateBenefit(welfare, user);

        // then
        assertThat(result).isEmpty();

        verify(welfareRegistrationRepository, times(1)).findByWelfareAndUser(welfare, user);
    }
}
