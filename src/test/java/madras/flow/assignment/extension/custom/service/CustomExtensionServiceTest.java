package madras.flow.assignment.extension.custom.service;

import lombok.extern.slf4j.Slf4j;
import madras.flow.assignment.extension.custom.dto.CustomExtensionReqDTO;
import madras.flow.assignment.extension.custom.dto.CustomExtensionResDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * title : CustomExtensionServiceImplTest
 *
 * description : Service 로직 테스트, 실제 환경처럼 모든 빈이 등록되어 사용됩니다.
 *               현재 적용한 환경변수 : local
 * reference :
 *
 * author : 임현영
 * date : 2024.05.30
 **/
@SpringBootTest(properties = "spring.profiles.active:local")
@Slf4j
class CustomExtensionServiceTest {
    @Autowired
    private CustomExtensionService customExtensionService;

    private CustomExtensionReqDTO.deleteInfo deleteInfo;
    private CustomExtensionReqDTO.insertInfo insertInfo;

    //요청값 - 필요에 따라 값 수정 진행
    @BeforeEach
    void setUp() {
        deleteInfo = CustomExtensionReqDTO.deleteInfo.builder()
                .deletedExtension("py")
                .build();

        insertInfo = CustomExtensionReqDTO.insertInfo.builder()
                .insertedExtension("show")
                .build();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Transactional(readOnly = true)
    void selectCustomExtensionList() {
        //1. 리스트 0인경우
        //2. 리스트 0보다 큰경우
        CustomExtensionResDTO.extensionList extensionList = customExtensionService.selectCustomExtensionList();

        assertThat(extensionList.getExtensionList().size()).isEqualTo(10);
    }

    @Test
    @Transactional
    void insertCustomExtension() {
        // 1. insert시 기존과 겹치는 경우
        // 2. insert시 새로운거 등록되는 경우
        // 3. insert 정보가 20자 이상인경우
        // 4. list가 200개 이상인경우
        // 5. 요청값 누락된경우
        CustomExtensionResDTO.extensionList extensionList = customExtensionService.insertCustomExtension(insertInfo);

        assertThat(extensionList.getExtensionList().size()).isEqualTo(10);
    }

    @Test
    @Transactional
    void deleteCustomExtension() {
        // 1. delete시 없는 확장자 지울려고 하는경우
        // 2. delete시 있는 확장자 지우는 경우
        // 3. 요청값 누락된경우
        CustomExtensionResDTO.extensionList extensionList = customExtensionService.deleteCustomExtension(deleteInfo);

        assertThat(extensionList.getExtensionList().size()).isEqualTo(10);
    }
}