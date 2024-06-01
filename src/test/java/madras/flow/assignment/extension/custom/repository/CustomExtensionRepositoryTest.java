package madras.flow.assignment.extension.custom.repository;

import lombok.extern.slf4j.Slf4j;
import madras.flow.assignment.extension.custom.dto.CustomExtensionReqDTO;
import madras.flow.assignment.extension.custom.entity.CustomExtensionEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
/**
 * title : CustomExtensionRepositoryTest
 *
 * description : Repository 테스트, DB관련 빈만 등록
 *               기본적인 검증은 ServiceTest로 진행하고, 해당 테스트코드는 오류 시 쿼리 확인용 입니다.
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.05.30
 **/
@MybatisTest
// 실 데이터베이스에 연결 시 필요한 어노테이션
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class CustomExtensionRepositoryTest {
    @Autowired
    private CustomExtensionRepository customExtensionRepository;

    private CustomExtensionEntity.deleteInfo deleteInfo;
    private CustomExtensionEntity.insertInfo insertInfo;

    @BeforeEach
    void setUp() {
        deleteInfo = CustomExtensionEntity.deleteInfo.builder()
                .deletedExtension("py")
                .build();

        insertInfo = CustomExtensionEntity.insertInfo.builder()
                .insertedExtension("show")
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Transactional(readOnly = true)
    void selectCustomExtensionList() {
        List<CustomExtensionEntity.extension> extensionList =
                customExtensionRepository.selectCustomExtensionList();

        assertThat(extensionList.size()).isEqualTo(10);
    }

    @Test
    @Transactional(readOnly = true)
    void countCustomExtensionList() {
        Integer count = customExtensionRepository.countCustomExtensionList();

        assertThat(count).isEqualTo(10);
    }

    @Test
    @Transactional(readOnly = true)
    void isRequestExtensionExist() {
        Integer count = customExtensionRepository.isRequestExtensionExist(insertInfo);

        assertThat(count).isEqualTo(1);
    }

    @Test
    @Transactional
    void insertCustomExtension() {
        Integer count = customExtensionRepository.insertCustomExtension(insertInfo);

        assertThat(count).isEqualTo(1);
    }

    @Test
    @Transactional
    void deleteCustomExtension() {
        Integer count = customExtensionRepository.deleteCustomExtension(deleteInfo);

        assertThat(count).isEqualTo(1);
    }
}