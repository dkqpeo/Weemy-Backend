package wemmy.service.benefit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wemmy.domain.area.Regions;
import wemmy.domain.area.city.SidoAreas;
import wemmy.domain.area.district.SiguAreas;
import wemmy.domain.area.district.UmdAreas;
import wemmy.domain.user.UserEntityV2;
import wemmy.domain.user.constant.Role;
import wemmy.domain.user.constant.UserType;
import wemmy.domain.welfare.Program;
import wemmy.domain.welfare.Wcategory;
import wemmy.domain.welfare.Welfare;
import wemmy.dto.scrap.ScrapDTO;
import wemmy.dto.welfare.benefit.BenefitDTO;
import wemmy.service.area.AreaService;
import wemmy.service.user.UserServiceV2;
import wemmy.service.welfare.ProgramService;
import wemmy.service.welfare.WelfareService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("BenefitServiceV2 테스트")
class BenefitServiceV2Test {

    @Mock
    private ProgramService programService;

    @Mock
    private WelfareService welfareService;

    @Mock
    private UserServiceV2 userService;

    @Mock
    private AreaService areaService;

    @InjectMocks
    private BenefitServiceV2 benefitServiceV2;

    private UserEntityV2 testUser;
    private Regions testRegion;
    private Regions governmentRegion;
    private Welfare testWelfare;
    private Program testProgram;
    private Wcategory testWcategory;
    private SidoAreas testSido;
    private SiguAreas testSigu;
    private UmdAreas testUmd;
    private List<ScrapDTO.response> emptyScrapList;

    @BeforeEach
    void setUp() {
        // 시도 설정
        testSido = SidoAreas.builder()
                .id(1L)
                .adm_code("11")
                .name("서울특별시")
                .build();

        // 시군구 설정
        testSigu = SiguAreas.builder()
                .id(1L)
                .adm_code("680")
                .name("강남구")
                .sido_id(testSido)
                .build();

        // 읍면동 설정
        testUmd = UmdAreas.builder()
                .id(1L)
                .adm_code("101")
                .name("역삼동")
                .sigu_id(testSigu)
                .build();

        // 지역 설정
        testRegion = Regions.builder()
                .id(1L)
                .region_cd("1168010100")
                .sido_id(testSido)
                .sigu_id(testSigu)
                .umd_id(testUmd)
                .build();

        // 정부 지역 설정
        SidoAreas govSido = SidoAreas.builder()
                .id(90L)
                .adm_code("90")
                .name("정부")
                .build();

        SiguAreas govSigu = SiguAreas.builder()
                .id(90L)
                .adm_code("000")
                .name("정부")
                .sido_id(govSido)
                .build();

        UmdAreas govUmd = UmdAreas.builder()
                .id(90L)
                .adm_code("000")
                .name("정부")
                .sigu_id(govSigu)
                .build();

        governmentRegion = Regions.builder()
                .id(90L)
                .region_cd("9000000000")
                .sido_id(govSido)
                .sigu_id(govSigu)
                .umd_id(govUmd)
                .build();

        // 카테고리 설정
        testWcategory = Wcategory.builder()
                .id(1L)
                .name("임산부")
                .build();

        // 사용자 설정
        testUser = UserEntityV2.builder()
                .id(1L)
                .email("test@test.com")
                .name("테스트유저")
                .userType(UserType.LOCAL)
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .sigu_id(testSigu)
                .babyState(null) // 임신
                .build();

        // 복지 정보 설정
        testWelfare = Welfare.builder()
                .id(1L)
                .uniqueId(1000L)
                .title("테스트 복지혜택")
                .field("건강")
                .content("테스트 내용")
                .way("신청 방법")
                .etc("기타사항")
                .originalUrl("https://test.com")
                .imageUrl("https://test.com/image.png")
                .adminId(testUser)
                .wCategoryId(testWcategory)
                .hostId(testRegion)
                .build();

        // 프로그램 정보 설정
        testProgram = Program.builder()
                .id(1L)
                .title("테스트 프로그램")
                .applicationPeriod("2024-01-01 ~ 2024-12-31")
                .trainingPeriod("2024-02-01 ~ 2024-11-30")
                .category("영유아")
                .cityName(testRegion)
                .imageUrl("https://test.com/program.png")
                .adminId(testUser)
                .build();

        // 빈 스크랩 리스트
        emptyScrapList = new ArrayList<>();
    }

    @Test
    @DisplayName("getBenefitTitleList - 맞춤혜택 복지 제목 리스트 조회 성공")
    void getBenefitTitleList_Success() {
        // given
        List<Welfare> welfareList = Arrays.asList(testWelfare);
        List<Welfare> governmentList = new ArrayList<>();
        List<Program> programList = Arrays.asList(testProgram);

        given(welfareService.findAllByWelfareById(testRegion)).willReturn(welfareList);
        given(welfareService.findAllByWelfareById(governmentRegion)).willReturn(governmentList);
        given(programService.findAllProgramByRegions(testRegion)).willReturn(programList);

        // when
        List<BenefitDTO.benefitTitleResponse> result = benefitServiceV2.getBenefitTitleList(
                testRegion, governmentRegion, "서울특별시", "강남구", testUser, emptyScrapList, "영유아"
        );

        // then
        assertThat(result).isNotNull();
        verify(welfareService).findAllByWelfareById(testRegion);
        verify(welfareService).findAllByWelfareById(governmentRegion);
        verify(programService).findAllProgramByRegions(testRegion);
    }

    @Test
    @DisplayName("getBenefitTitleListByGroup - 그룹별 복지 제목 리스트 조회 성공")
    void getBenefitTitleListByGroup_Success() {
        // given
        List<Welfare> welfareList = Arrays.asList(testWelfare);
        List<Welfare> governmentList = new ArrayList<>();
        List<Program> programList = Arrays.asList(testProgram);

        given(welfareService.findAllByWelfareById(testRegion)).willReturn(welfareList);
        given(welfareService.findAllByWelfareById(governmentRegion)).willReturn(governmentList);
        given(programService.findAllProgramByRegions(testRegion)).willReturn(programList);

        // when
        List<BenefitDTO.benefitTitleResponse> result = benefitServiceV2.getBenefitTitleListByGroup(
                testRegion, governmentRegion, "서울특별시", "강남구", testUser, emptyScrapList, "영유아"
        );

        // then
        assertThat(result).isNotNull();
        verify(welfareService).findAllByWelfareById(testRegion);
        verify(welfareService).findAllByWelfareById(governmentRegion);
        verify(programService).findAllProgramByRegions(testRegion);
    }

    @Test
    @DisplayName("getBenefitTitleListByMostView - Most View 복지 리스트 조회 성공")
    void getBenefitTitleListByMostView_Success() {
        // given
        // Most View는 최소 3개 이상의 데이터가 필요
        Welfare welfare2 = Welfare.builder()
                .id(2L)
                .uniqueId(2000L)
                .field("건강")
                .content("테스트 내용 2")
                .way("신청 방법")
                .etc("기타사항")
                .originalUrl("https://test.com")
                .imageUrl("https://test.com/image2.png")
                .adminId(testUser)
                .wCategoryId(testWcategory)
                .hostId(testRegion)
                .build();

        Welfare welfare3 = Welfare.builder()
                .id(3L)
                .uniqueId(3000L)
                .title("테스트 복지혜택 3")
                .field("건강")
                .content("테스트 내용 3")
                .way("신청 방법")
                .etc("기타사항")
                .originalUrl("https://test.com")
                .imageUrl("https://test.com/image3.png")
                .adminId(testUser)
                .wCategoryId(testWcategory)
                .hostId(testRegion)
                .build();

        Program program2 = Program.builder()
                .id(2L)
                .title("테스트 프로그램 2")
                .applicationPeriod("2024-01-01 ~ 2024-12-31")
                .trainingPeriod("2024-02-01 ~ 2024-11-30")
                .category("영유아")
                .cityName(testRegion)
                .imageUrl("https://test.com/program2.png")
                .adminId(testUser)
                .build();

        List<Welfare> welfareList = Arrays.asList(testWelfare, welfare2, welfare3);
        List<Program> programList = Arrays.asList(testProgram, program2);

        given(welfareService.findAllByView(testRegion)).willReturn(welfareList);
        given(programService.findByView(testRegion)).willReturn(programList);

        // when
        List<BenefitDTO.benefitTitleResponse> result = benefitServiceV2.getBenefitTitleListByMostView(
                testRegion, "서울특별시", "강남구", testUser, emptyScrapList
        );

        // then
        assertThat(result).isNotNull();
        verify(welfareService).findAllByView(testRegion);
        verify(programService).findByView(testRegion);
    }

    @Test
    @DisplayName("getBenefitTitleListWeb - 웹 지역시 기준 복지 리스트 조회 성공")
    void getBenefitTitleListWeb_Success() {
        // given
        List<Welfare> welfareList = Collections.singletonList(testWelfare);
        List<Welfare> governmentList = new ArrayList<>();
        List<Program> programList = Collections.singletonList(testProgram);

        given(welfareService.findAll()).willReturn(welfareList);
        given(welfareService.findAllByWelfareById(governmentRegion)).willReturn(governmentList);
        given(programService.findAll()).willReturn(programList);

        // when
        List<BenefitDTO.titleResponseWeb> result = benefitServiceV2.getBenefitTitleListWeb(
                governmentRegion, "서울특별시", "강남구"
        );

        // then
        assertThat(result).isNotNull();
        verify(welfareService).findAll();
        verify(welfareService).findAllByWelfareById(governmentRegion);
        verify(programService).findAll();
    }

    @Test
    @DisplayName("getBenefitDetail - 복지 상세 정보 조회 성공")
    void getBenefitDetail_Success() {
        // given
        Long benefitId = 1L;
        given(welfareService.findById(benefitId)).willReturn(testWelfare);

        // when
        BenefitDTO.benefitResponse result = benefitServiceV2.getBenefitDetail(
                benefitId, "false", "benefit"
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result.getBenefitId()).isEqualTo(benefitId);
        assertThat(result.getTitle()).isEqualTo("테스트 복지혜택");
        assertThat(result.getCity()).isEqualTo("서울특별시");
        assertThat(result.getDistrict()).isEqualTo("강남구");
        assertThat(result.getScrap()).isEqualTo("false");
        verify(welfareService).findById(benefitId);
    }

    @Test
    @DisplayName("getBenefitDetail - 스크랩된 복지 상세 정보 조회 성공")
    void getBenefitDetail_WithScrap_Success() {
        // given
        Long benefitId = 1L;
        given(welfareService.findById(benefitId)).willReturn(testWelfare);

        // when
        BenefitDTO.benefitResponse result = benefitServiceV2.getBenefitDetail(
                benefitId, "true", "benefit"
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result.getScrap()).isEqualTo("true");
        verify(welfareService).findById(benefitId);
    }

    @Test
    @DisplayName("getProgramDetail - 프로그램 상세 정보 조회 성공")
    void getProgramDetail_Success() {
        // given
        Long programId = 1L;
        given(programService.findById(programId)).willReturn(testProgram);

        // when
        BenefitDTO.programResponse result = benefitServiceV2.getProgramDetail(
                programId, "false", "program"
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result.getBenefitId()).isEqualTo(programId);
        assertThat(result.getTitle()).isEqualTo("테스트 프로그램");
        assertThat(result.getCity()).isEqualTo("서울특별시");
        assertThat(result.getDistrict()).isEqualTo("강남구");
        assertThat(result.getScrap()).isEqualTo("false");
        assertThat(result.getApplicationPeriod()).isEqualTo("2024-01-01 ~ 2024-12-31");
        assertThat(result.getTrainingPeriod()).isEqualTo("2024-02-01 ~ 2024-11-30");
        verify(programService).findById(programId);
    }

    @Test
    @DisplayName("getProgramDetail - 스크랩된 프로그램 상세 정보 조회 성공")
    void getProgramDetail_WithScrap_Success() {
        // given
        Long programId = 1L;
        given(programService.findById(programId)).willReturn(testProgram);

        // when
        BenefitDTO.programResponse result = benefitServiceV2.getProgramDetail(
                programId, "true", "program"
        );

        // then
        assertThat(result).isNotNull();
        assertThat(result.getScrap()).isEqualTo("true");
        verify(programService).findById(programId);
    }

    @Test
    @DisplayName("getBenefitTitleList - 육아 카테고리 사용자 테스트")
    void getBenefitTitleList_WithBabyState_Success() {
        // given
        UserEntityV2 userWithBaby = UserEntityV2.builder()
                .id(2L)
                .email("test2@test.com")
                .name("육아유저")
                .userType(UserType.LOCAL)
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .sigu_id(testSigu)
                .babyState(Collections.singletonList("영유아")) // 육아
                .build();

        Wcategory babyCategory = Wcategory.builder()
                .id(2L)
                .name("영유아")
                .build();

        Welfare babyWelfare = Welfare.builder()
                .id(2L)
                .uniqueId(2000L)
                .title("육아 복지혜택")
                .field("육아")
                .content("육아 내용")
                .way("신청 방법")
                .etc("기타사항")
                .originalUrl("https://test.com")
                .imageUrl("https://test.com/image2.png")
                .adminId(testUser)
                .wCategoryId(babyCategory)
                .hostId(testRegion)
                .build();

        List<Welfare> welfareList = Arrays.asList(babyWelfare);
        List<Welfare> governmentList = new ArrayList<>();
        List<Program> programList = Arrays.asList(testProgram);

        given(welfareService.findAllByWelfareById(testRegion)).willReturn(welfareList);
        given(welfareService.findAllByWelfareById(governmentRegion)).willReturn(governmentList);
        given(programService.findAllProgramByRegions(testRegion)).willReturn(programList);

        // when
        List<BenefitDTO.benefitTitleResponse> result = benefitServiceV2.getBenefitTitleList(
                testRegion, governmentRegion, "서울특별시", "강남구", userWithBaby, emptyScrapList, "영유아"
        );

        // then
        assertThat(result).isNotNull();
        verify(welfareService).findAllByWelfareById(testRegion);
        verify(welfareService).findAllByWelfareById(governmentRegion);
        verify(programService).findAllProgramByRegions(testRegion);
    }
}
