package madras.flow.assignment.common.aop;

import lombok.extern.slf4j.Slf4j;
import madras.flow.assignment.common.enums.ErrorEnum;
import madras.flow.assignment.common.exception.custom.CustomDBException;
import madras.flow.assignment.common.exception.custom.CustomRequestException;
import madras.flow.assignment.common.exception.custom.CustomServiceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * title : TimeTraceAop
 *
 * description : 함수 실행 시간에 대해 AOP 적용
 *
 * reference :  시간 aop : https://hseungyeon.tistory.com/349
 *              aop 어노테이션 : https://programforlife.tistory.com/107 , https://code-lab1.tistory.com/193
 *
 * author : 임현영
 * date : 2024.05.30
 **/
@Slf4j
@Aspect
@Component
public class TimeTraceAop {
    // 공통관심사항을 적용할 곳, Controller만 타겟팅
    @Around("execution(* madras.flow.assignment.extension..controller..*(..))")

    /*
    * 매 함수의 실행시간을 체크하는 기능
    * */
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();    // 시작 시각

        try {
            return joinPoint.proceed();
        }
        catch (Exception e){
            log.info("AOP 함수 실행 중 {} 메소드의 오류 캐치",getMethod(joinPoint));
            //globalException Handler로 에러 위임
            throw e;
        }
        finally {
            long finish = System.currentTimeMillis();   // 종료 시각
            long timeMs = finish - start;   // 호출 시간

            log.info("--------------------------------------------------------------------------------");
            log.info("실행 함수 : {}",joinPoint.getSignature().toShortString());
            log.info("서비스 수행시간 : {}", timeMs + "ms");
            log.info("--------------------------------------------------------------------------------");
        }
    }

    // JoinPoint로 메서드 정보 가져오기
    private String getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.toShortString();
    }
}