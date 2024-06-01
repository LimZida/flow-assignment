package madras.flow.assignment.extension.fix.service;

import lombok.extern.slf4j.Slf4j;
import madras.flow.assignment.extension.fix.dto.FixExtensionReqDTO;
import madras.flow.assignment.extension.fix.dto.FixExtensionResDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * title : FixExtensionServiceImplTest
 *
 * description : Service 로직 테스트, 실제 환경처럼 모든 빈이 등록되어 사용됩니다.
 *               현재 적용한 환경변수 : local
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.05.30
 **/
@SpringBootTest(properties = "spring.profiles.active:local")
@Slf4j
class FixExtensionServiceTest {
    @Autowired
    private FixExtensionService fixExtensionService;

    private FixExtensionReqDTO.updateInfo updateInfo;
    @BeforeEach
    void setUp() {
        updateInfo = FixExtensionReqDTO.updateInfo.builder()
                .updatedExtension("exe")
                .useYn("N")
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Transactional(readOnly = true)
    void selectFixExtensionList() {
        //1. 리스트 0인경우
        //2. 리스트 0보다 큰경우
        FixExtensionResDTO.extensionList extensionList = fixExtensionService.selectFixExtensionList();

        assertThat(extensionList.getExtensionList().size()).isEqualTo(5);
    }

    @Test
    @Transactional
    void updateFixExtension() {
        //1. 변경 요청 정보와 현재 DB 정보가 동일할경우
        //2. 정상적으로 업데이트된경우
        //3. 요청값 누락된 경우
        FixExtensionResDTO.extensionList extensionList = fixExtensionService.updateFixExtension(updateInfo);

        assertThat(extensionList.getExtensionList().size()).isEqualTo(5);
    }
}