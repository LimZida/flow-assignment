package madras.flow.assignment.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import madras.flow.assignment.common.enums.ErrorEnum;
import madras.flow.assignment.common.exception.custom.CustomDBException;
import madras.flow.assignment.common.exception.custom.CustomRequestException;
import madras.flow.assignment.common.exception.custom.CustomServiceException;
import madras.flow.assignment.common.exception.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.ResponseEntity.status;

/**
 * title : GlobalExceptionHandler
 *
 * description : 예외 발생 시 처리해주는 핸들러
 *               Exception - 예기치 못한 에러
 *               NullPointerException - NULL 에러
 *               CustomDBException - DB 처리 에러
 *               CustomServiceException - 로직 예외 처리
 *               CustomRequestException - 요청값 에러
 *
 *
 * reference :  Exception Handler : https://velog.io/@kiiiyeon/%EC%8A%A4%ED%94%84%EB%A7%81-ExceptionHandler%EB%A5%BC-%ED%86%B5%ED%95%9C-%EC%98%88%EC%99%B8%EC%B2%98%EB%A6%AC
 *
 *
 * author : 임현영
 * date : 2024.05.31
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // DB 조회 에러
    @ExceptionHandler(CustomDBException.class)
    public ResponseEntity<Object> handleDBException(CustomDBException e) {
        ExceptionDto exceptionDTO = getExceptionDTO(e.getErrorCode(), e.getMessage(), e.getE().getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionDTO);
    }

    // 로직 처리 예외
    @ExceptionHandler(CustomServiceException.class)
    public ResponseEntity<Object> handleServiceException(CustomServiceException e) {
        ExceptionDto exceptionDTO = getExceptionDTO(e.getErrorCode(), e.getMessage(), e.getE().getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionDTO);
    }

    // 요청 처리 예외
    @ExceptionHandler(CustomRequestException.class)
    public ResponseEntity<Object> handleRequestException(CustomRequestException e) {
        ExceptionDto exceptionDTO = getExceptionDTO(e.getErrorCode(), e.getMessage(), e.getE().getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionDTO);
    }

    // Null 에러
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullException(NullPointerException e) {
        ExceptionDto exceptionDTO = getExceptionDTO(ErrorEnum.LGC02.name(), ErrorEnum.LGC02.getValue(), e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionDTO);
	}

    // 예기치 못한 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        ExceptionDto exceptionDTO = getExceptionDTO(ErrorEnum.LGC99.name(), ErrorEnum.LGC99.getValue(), e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionDTO);
    }


    // error DTO 생성
    private ExceptionDto getExceptionDTO(String errorCode, String errorMessage, String errorDetailMessage){
        return ExceptionDto.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .errorDetailMessage(errorDetailMessage)
                .build();
    }
}
