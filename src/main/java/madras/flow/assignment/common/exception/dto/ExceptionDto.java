package madras.flow.assignment.common.exception.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * title : ExceptionDto
 *
 * description : 에러 시 ResponseEntity Body에 들어갈 ErrorDto
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.05.31
 **/
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE) //파라미터가 없는 생성자 생성
public class ExceptionDto {
    private String errorCode;
    private String errorMessage;
    private String errorDetailMessage;

    @Builder
    private ExceptionDto(String errorCode, String errorMessage, String errorDetailMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetailMessage = errorDetailMessage;
    }
}
