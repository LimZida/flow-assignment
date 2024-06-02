package madras.flow.assignment.extension.fix.dto;

import lombok.*;
import madras.flow.assignment.extension.custom.dto.CustomExtensionResDTO;

import java.util.List;
/**
 * title : FixExtensionResDTO
 *
 * description : 고정확장자 응답 매핑용 dto ( Controller <-> Service간 사용)
 *
 * reference : innerClass로 DTO 관리하기 https://velog.io/@kjyeon1101/Spring
 *
 * author : 임현영
 * date : 2024.05.30
 **/
public class FixExtensionResDTO {
    // 고정 확장자 리스트
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

    // 고정 확장자
    //       {
    //           "extensionName": "etc",
    //           "extensionUsage": "Y"
    //       }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE) //파라미터가 없는 생성자 생성
    @ToString
    public static class extension {
        private String extensionName;
        private String extensionUsage;

        @Builder
        private extension(String extensionName, String extensionUsage){
            this.extensionName = extensionName;
            this.extensionUsage = extensionUsage;
        }
    }

    // 고정 사용여부
    //       {
    //           "extensionUsage": "Y"
    //       }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE) //파라미터가 없는 생성자 생성
    @ToString
    public static class usage {
        private String extensionUsage;

        @Builder
        private usage(String extensionUsage){
            this.extensionUsage = extensionUsage;
        }
    }
}
