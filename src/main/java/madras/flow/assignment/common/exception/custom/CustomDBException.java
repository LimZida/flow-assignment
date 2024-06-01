package madras.flow.assignment.common.exception.custom;

import lombok.Getter;
import madras.flow.assignment.common.enums.CodeEnum;

/**
 * title : CustomDBException
 *
 * description : DAO 예외 응답처리 시 커스텀하여 사용할 CustomException
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.05.31
 **/
@Getter
public class CustomDBException extends RuntimeException {
    private String errorCode;
    private Exception e;

    public CustomDBException(String errorCode, String message , Exception e) {
        super(message);
        this.errorCode = errorCode;
        this.e = e != null ? e : new Exception(CodeEnum.EMPTY.getValue());
    }
}
