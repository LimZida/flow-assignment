package madras.flow.assignment.extension.custom.dto;

import lombok.*;

import java.util.List;

/**
 * title : CustomExtensionDTO
 *
 * description : 커스텀확장자 응답 매핑용 dto
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.05.30
 **/
public class CustomExtensionResDTO {
    // 커스텀 확장자 리스트
    // ex)
    //    "extensionList": [
    //        {
    //            "extensionName": "etc",
    //            "extensionUsage": "Y",
    //        },
    //        {
    //            "extensionName": "etc2",
    //            "extensionUsage": "Y",
    //        } ...
    //    ]
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE) //파라미터가 없는 생성자 생성
    @ToString
    public static class extensionList {
        private List<extension> extensionList;

        @Builder
        private extensionList(List<extension> extensionList){
            this.extensionList = extensionList;
        }
    }

    // 커스텀 확장자
    //       {
    //           "extensionName": "etc",
    //           "extensionUsage": "Y"
    //       }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE) //파라미터가 없는 생성자 생성
    @ToString
    public static class extension {
        private String extensionName;

        @Builder
        private extension(String extensionName){
            this.extensionName = extensionName;
        }
    }
}
