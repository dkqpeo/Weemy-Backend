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
import wemmy.domain.welfare.Program;
import wemmy.domain.welfare.ProgramRegistration;
import wemmy.domain.welfare.Welfare;
import wemmy.domain.welfare.WelfareRegistration;
import wemmy.dto.welfare.WelfareRegisterListRespDTO;
import wemmy.dto.welfare.program.ProgramRegisterDTO;
import wemmy.global.config.error.exception.ControllerException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WelfareRegisterationServiceTest {

    @Mock
    private ProgramRegistrationService programRegistrationService;

    @Mock
    private BenefitRegistrationService benefitRegistrationService;

    @InjectMocks
    private WelfareRegisterationService welfareRegisterationService;

    private UserEntityV2 user;
    private Welfare welfare;
    private Program program;
    private ProgramRegisterDTO registerDTO;
    private ProgramRegistration programRegistration;
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

        program = Program.builder()
                .id(1L)
                .title("육아 교실")
                .applicationPeriod("2024-01-01 ~ 2024-12-31")
                .trainingPeriod("2024-03-01 ~ 2024-06-30")
                .category("출산·육아")
                .view(0L)
                .build();

        registerDTO = new ProgramRegisterDTO();
        registerDTO.setName("홍길동");
        registerDTO.setBirthday(LocalDate.of(1990, 1, 1));
        registerDTO.setAddress("서울시 강남구");
        registerDTO.setAddressDetail("테헤란로 123");
        registerDTO.setPhone("010-1234-5678");
        registerDTO.setEmail("test@example.com");

        programRegistration = ProgramRegistration.builder()
                .id(1L)
                .name("홍길동")
                .birthday(LocalDate.of(1990, 1, 1))
                .address("서울시 강남구")
                .addressDetail("테헤란로 123")
                .phone("010-1234-5678")
                .email("test@example.com")
                .program(program)
                .user(user)
                .createTime(LocalDateTime.now())
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
    @DisplayName("프로그램 등록 성공")
    void programRegister_Success() {
        // given
        doNothing().when(programRegistrationService).register(any(ProgramRegistration.class));

        // when
        welfareRegisterationService.programRegister(registerDTO, user, program);

        // then
        verify(programRegistrationService, times(1)).register(any(ProgramRegistration.class));
    }

    @Test
    @DisplayName("복지 등록 성공")
    void welfareRegister_Success() {
        // given
        doNothing().when(benefitRegistrationService).register(any(WelfareRegistration.class));

        // when
        welfareRegisterationService.welfareRegister(registerDTO, user, welfare);

        // then
        verify(benefitRegistrationService, times(1)).register(any(WelfareRegistration.class));
    }

    @Test
    @DisplayName("등록 리스트 조회 성공")
    void registerList_Success() {
        // given
        List<ProgramRegistration> programList = List.of(programRegistration);
        List<WelfareRegistration> benefitList = List.of(welfareRegistration);

        when(programRegistrationService.findAll()).thenReturn(programList);
        when(benefitRegistrationService.findAll()).thenReturn(benefitList);

        // when
        List<WelfareRegisterListRespDTO.response> result = welfareRegisterationService.registerList();

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);

        // 프로그램 그룹 검증
        WelfareRegisterListRespDTO.response programResponse = result.get(0);
        assertThat(programResponse.getGroup()).isEqualTo("program");
        assertThat(programResponse.getData()).hasSize(1);
        assertThat(programResponse.getData().get(0).getTitle()).isEqualTo("육아 교실");
        assertThat(programResponse.getData().get(0).getName()).isEqualTo("홍길동");

        // 복지 그룹 검증
        WelfareRegisterListRespDTO.response benefitResponse = result.get(1);
        assertThat(benefitResponse.getGroup()).isEqualTo("benefit");
        assertThat(benefitResponse.getData()).hasSize(1);
        assertThat(benefitResponse.getData().get(0).getTitle()).isEqualTo("출산 지원금");
        assertThat(benefitResponse.getData().get(0).getName()).isEqualTo("홍길동");

        verify(programRegistrationService, times(1)).findAll();
        verify(benefitRegistrationService, times(1)).findAll();
    }

    @Test
    @DisplayName("등록 리스트 조회 - 빈 리스트")
    void registerList_EmptyList() {
        // given
        when(programRegistrationService.findAll()).thenReturn(new ArrayList<>());
        when(benefitRegistrationService.findAll()).thenReturn(new ArrayList<>());

        // when
        List<WelfareRegisterListRespDTO.response> result = welfareRegisterationService.registerList();

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getData()).isEmpty();
        assertThat(result.get(1).getData()).isEmpty();

        verify(programRegistrationService, times(1)).findAll();
        verify(benefitRegistrationService, times(1)).findAll();
    }

    @Test
    @DisplayName("프로그램 중복 검증 - 이미 등록됨 (예외 발생)")
    void validateProgram_AlreadyRegistered() {
        // given
        when(programRegistrationService.validateProgram(program, user))
                .thenReturn(Optional.of(programRegistration));

        // when & then
        assertThatThrownBy(() -> welfareRegisterationService.validateProgram(user, program))
                .isInstanceOf(ControllerException.class);

        verify(programRegistrationService, times(1)).validateProgram(program, user);
    }

    @Test
    @DisplayName("프로그램 중복 검증 - 등록되지 않음 (정상)")
    void validateProgram_NotRegistered() {
        // given
        when(programRegistrationService.validateProgram(program, user))
                .thenReturn(Optional.empty());

        // when
        welfareRegisterationService.validateProgram(user, program);

        // then
        verify(programRegistrationService, times(1)).validateProgram(program, user);
    }

    @Test
    @DisplayName("복지 중복 검증 - 이미 등록됨 (예외 발생)")
    void validateBenefit_AlreadyRegistered() {
        // given
        when(benefitRegistrationService.validateBenefit(welfare, user))
                .thenReturn(Optional.of(welfareRegistration));

        // when & then
        assertThatThrownBy(() -> welfareRegisterationService.validateBenefit(user, welfare))
                .isInstanceOf(ControllerException.class);

        verify(benefitRegistrationService, times(1)).validateBenefit(welfare, user);
    }

    @Test
    @DisplayName("복지 중복 검증 - 등록되지 않음 (정상)")
    void validateBenefit_NotRegistered() {
        // given
        when(benefitRegistrationService.validateBenefit(welfare, user))
                .thenReturn(Optional.empty());

        // when
        welfareRegisterationService.validateBenefit(user, welfare);

        // then
        verify(benefitRegistrationService, times(1)).validateBenefit(welfare, user);
    }
}
