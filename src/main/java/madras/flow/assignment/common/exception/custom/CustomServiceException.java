package madras.flow.assignment.common.exception.custom;

import lombok.Getter;
import madras.flow.assignment.common.enums.CodeEnum;

/**
 * title : CustomException
 *
 * description : Service 예외 응답처리 시 커스텀하여 사용할 CustomException
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.04.16
 **/
@Getter
public class CustomServiceException extends RuntimeException {
    private String errorCode;
    private Exception e;

    public CustomServiceException(String errorCode, String message , Exception e) {
        super(message);
        this.errorCode = errorCode;
        this.e = e != null ? e : new Exception(CodeEnum.EMPTY.getValue());
    }
}
