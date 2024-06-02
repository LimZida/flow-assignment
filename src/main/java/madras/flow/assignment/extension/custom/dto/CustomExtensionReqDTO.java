package madras.flow.assignment.extension.custom.dto;

import lombok.*;
import madras.flow.assignment.common.enums.ErrorEnum;
import madras.flow.assignment.common.exception.custom.CustomRequestException;

/**
 * title : CustomExtensionDTO
 *
 * description : 커스텀확장자 요청 매핑용 dto
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.05.30
 **/
public class CustomExtensionReqDTO {
    // 커스텀 확장자 등록요청
    // ex)
    //      "insertedExtension" : "exe"
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE) //파라미터가 없는 생성자 생성
    @ToString
    public static class insertInfo {
        private String insertedExtension;

        @Builder
        private insertInfo(String insertedExtension){
            this.insertedExtension = insertedExtension;
        }

        // 요청값 검증
        public void validate(){
            // 확장자명 입력길이 20 초과
            if (insertedExtension.length() > 20){
                throw new CustomRequestException(ErrorEnum.CEC04.name(), ErrorEnum.CEC04.getValue(), null);
            }

            // 요청값이 빈값인 경우
            if (insertedExtension.isEmpty()){
                throw new CustomRequestException(ErrorEnum.LGC01.name(),
                        ErrorEnum.LGC01.getValue(), null);
            }
        }
    }

    // 커스텀 확장자 삭제요청
    // ex)
    //      "deletedExtension" : "exe"
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE) //파라미터가 없는 생성자 생성
    @ToString
    public static class deleteInfo {
        private String deletedExtension;

        @Builder
        private deleteInfo(String deletedExtension){
            this.deletedExtension = deletedExtension;
        }

        // 요청값이 검증
        public void validate(){
            // 요청값 빈값
            if (deletedExtension.isEmpty()){
                throw new CustomRequestException(ErrorEnum.LGC01.name(), ErrorEnum.LGC01.getValue(), null);
            }
        }
    }
}
