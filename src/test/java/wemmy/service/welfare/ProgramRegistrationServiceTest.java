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
import wemmy.repository.welfare.ProgramRegistrationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProgramRegistrationServiceTest {

    @Mock
    private ProgramRegistrationRepository programRegistrationRepository;

    @InjectMocks
    private ProgramRegistrationService programRegistrationService;

    private UserEntityV2 user;
    private Welfare welfare;
    private Program program;
    private ProgramRegistration programRegistration;

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
    }

    @Test
    @DisplayName("프로그램 등록 저장 성공")
    void register_Success() {
        // given
        when(programRegistrationRepository.save(any(ProgramRegistration.class)))
                .thenReturn(programRegistration);

        // when
        programRegistrationService.register(programRegistration);

        // then
        verify(programRegistrationRepository, times(1)).save(programRegistration);
    }

    @Test
    @DisplayName("전체 프로그램 등록 조회 성공")
    void findAll_Success() {
        // given
        List<ProgramRegistration> registrations = List.of(programRegistration);
        when(programRegistrationRepository.findAll()).thenReturn(registrations);

        // when
        List<ProgramRegistration> result = programRegistrationService.findAll();

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("홍길동");
        assertThat(result.get(0).getProgram().getTitle()).isEqualTo("육아 교실");

        verify(programRegistrationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("전체 프로그램 등록 조회 - 빈 리스트")
    void findAll_EmptyList() {
        // given
        when(programRegistrationRepository.findAll()).thenReturn(new ArrayList<>());

        // when
        List<ProgramRegistration> result = programRegistrationService.findAll();

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(programRegistrationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("프로그램 중복 검증 - 등록 이력 존재")
    void validateProgram_Exists() {
        // given
        when(programRegistrationRepository.findByProgramAndUser(program, user))
                .thenReturn(Optional.of(programRegistration));

        // when
        Optional<ProgramRegistration> result = programRegistrationService.validateProgram(program, user);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getProgram()).isEqualTo(program);
        assertThat(result.get().getUser()).isEqualTo(user);

        verify(programRegistrationRepository, times(1)).findByProgramAndUser(program, user);
    }

    @Test
    @DisplayName("프로그램 중복 검증 - 등록 이력 없음")
    void validateProgram_NotExists() {
        // given
        when(programRegistrationRepository.findByProgramAndUser(program, user))
                .thenReturn(Optional.empty());

        // when
        Optional<ProgramRegistration> result = programRegistrationService.validateProgram(program, user);

        // then
        assertThat(result).isEmpty();

        verify(programRegistrationRepository, times(1)).findByProgramAndUser(program, user);
    }
}
