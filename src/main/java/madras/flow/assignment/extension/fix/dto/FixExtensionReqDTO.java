package madras.flow.assignment.extension.fix.dto;

import lombok.*;
import madras.flow.assignment.common.enums.ErrorEnum;
import madras.flow.assignment.common.exception.custom.CustomRequestException;

/**
 * title : FixExtensionResDTO
 *
 * description : 고정확장자 요청 매핑용 dto
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.05.30
 **/
public class FixExtensionReqDTO {
    // 고정 확장자 사용,미사용요청
    // ex)
    //      "updatedExtension" : "exe"
    //      "useYn" : "Y" or "N'
    //
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE) //파라미터가 없는 생성자 생성
    @ToString
    public static class updateInfo {
        private String updatedExtension;
        private String useYn;

        @Builder
        private updateInfo(String updatedExtension, String useYn){
            this.updatedExtension = updatedExtension;
            this.useYn = useYn;
        }

        // 요청값 검증
        public void validate(){
            // 요청값이 빈값인 경우
            if (updatedExtension.isEmpty() || useYn.isEmpty()){
                throw new CustomRequestException(ErrorEnum.LGC01.name(), ErrorEnum.LGC01.getValue(), null);
            }
        }
    }
}
