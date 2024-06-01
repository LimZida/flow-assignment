package madras.flow.assignment.extension.fix.repository;

import lombok.extern.slf4j.Slf4j;
import madras.flow.assignment.extension.custom.entity.CustomExtensionEntity;
import madras.flow.assignment.extension.custom.repository.CustomExtensionRepository;
import madras.flow.assignment.extension.fix.entity.FixExtensionEntity;
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
 * title : FixExtensionRepositoryTest
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
class FixExtensionRepositoryTest {
    @Autowired
    private FixExtensionRepository fixExtensionRepository;

    private FixExtensionEntity.updateInfo updateInfo;

    @BeforeEach
    void setUp() {
        updateInfo = FixExtensionEntity.updateInfo.builder()
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
        List<FixExtensionEntity.extension> extensionList = fixExtensionRepository.selectFixExtensionList();

        assertThat(extensionList.size()).isEqualTo(5);
    }

    @Test
    @Transactional(readOnly = true)
    void selectFixExtensionUseYn() {
        FixExtensionEntity.usage usage = fixExtensionRepository.selectFixExtensionUseYn(updateInfo);

        assertThat(usage).isEqualTo("Y");
    }

    @Test
    @Transactional
    void updateFixExtension() {
        Integer count = fixExtensionRepository.updateFixExtension(updateInfo);

        assertThat(count).isEqualTo(1);
    }
}