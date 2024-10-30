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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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


    //   상황 분석
    //트랜잭션 1 (25714):
    //
    //SELECT COUNT(*) FROM custom_extension ce FOR UPDATE; 쿼리를 실행하여 custom_extension 테이블에 대해 X-lock을 설정합니다.
    //이 잠금은 테이블의 모든 행(여기서는 데이터가 없기 때문에 gap 영역까지 포함)에 대해 배타적 잠금을 설정합니다.
    // 이어서 INSERT INTO custom_extension ... 쿼리를 실행하여
    //`PRIMARY`에 대한 lock_mode X insert intention waiting을 통해 새로운 데이터를 삽입하려고 합니다.

    //트랜잭션 2 (25713):
    //
    //거의 동시에 SELECT COUNT(*) FROM custom_extension ce FOR UPDATE; 쿼리를 실행하려고 합니다.
    //트랜잭션 1이 이미 X-lock을 설정했기 때문에 트랜잭션 2는 트랜잭션 1이 끝날때까지 기다리게 됩니다.
    //그리고 INSERT INTO custom_extension ... 쿼리 실행도 대기합니다.

    //Deadlock 발생 과정
    //트랜잭션 1가 SELECT ... FOR UPDATE를 실행하고 테이블의 모든 행을 잠급니다.
    //트랜잭션 2가 SELECT COUNT(*) FROM custom_extension ce FOR UPDATE; 쿼리를 실행을 요청하지만 트랜잭션 A의 잠금 때문에 대기합니다.
    //트랜잭션 1가 INSERT 쿼리를 실행하려고 intention waiting lock을 시도할 때, 트랜잭션 2의 잠금 대기 때문에 데드락이 발생합니다.

    @Test
//    @Transactional
    void insertCustomExtension() throws InterruptedException {
        // 1. insert시 기존과 겹치는 경우
        // 2. insert시 새로운거 등록되는 경우
        // 3. insert 정보가 20자 이상인경우
        // 4. list가 200개 이상인경우
        // 5. 요청값 누락된경우
//        CustomExtensionResDTO.extensionList extensionList = customExtensionService.insertCustomExtension(insertInfo);


        // 동시성 멀티스레드 환경 구현 test
        // 부분적 validation 메소드 Synchronized 사용 불가 , size 공유하는 증상 있음
        // 전체 insertCustomExtension 메소드 Synchronized 사용 가능, size 공유하지 않음
        AtomicInteger listSize = new AtomicInteger();
        AtomicInteger count = new AtomicInteger(0);


        // 스레드 개수 (작업개수)
        int numberOfThreads = 5;
        // 풀 개수
        ExecutorService service = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
//        int count = 0;
        for (int i = 0; i < numberOfThreads; i++) {
//            int finalCount = count;
            int finalCount = count.getAndIncrement();
            service.submit(() -> {
                insertInfo = CustomExtensionReqDTO.insertInfo.builder()
                        .insertedExtension(String.valueOf(finalCount))
                        .build();
                CustomExtensionResDTO.extensionList extensionList = customExtensionService.insertCustomExtension(insertInfo);
                // 순차적 사이즈 확인
                log.info("#### size {}", extensionList.getExtensionList().size() );
                listSize.set(extensionList.getExtensionList().size());
                latch.countDown();
            });
//            Thread.sleep(1000); // 예시로 1초 대기
//            count++;
        }
        latch.await();

//        assertThat(extensionList.getExtensionList().size()).isEqualTo(200);
        assertThat(listSize).isEqualTo(50);
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