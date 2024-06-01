package madras.flow.assignment.common.exception.custom;

import lombok.Getter;
import madras.flow.assignment.common.enums.CodeEnum;

/**
 * title : CustomRequestException
 *
 * description : Controller 요청값 예외처리 시 커스텀하여 사용할 CustomException
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.05.30
 **/
@Getter
public class CustomRequestException extends RuntimeException {
    private String errorCode;
    private Exception e;

    public CustomRequestException(String errorCode, String message , Exception e) {
        super(message);
        this.errorCode = errorCode;
        this.e = e != null ? e : new Exception(CodeEnum.EMPTY.getValue());
    }
}
