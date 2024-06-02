package madras.flow.assignment.extension.fix.entity;

import lombok.*;
import madras.flow.assignment.extension.fix.dto.FixExtensionResDTO;

import java.util.List;

/**
 * title : FixExtensionEntity
 *
 * description : 고정확장자 DAO <=> DB 접근 entitu
 *
 * reference : innerClass로 DTO 관리하기 https://velog.io/@kjyeon1101/Spring
 *
 * author : 임현영
 * date : 2024.06.01
 **/
public class FixExtensionEntity {
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
        private updateInfo(String updatedExtension, String useYn) {
            this.updatedExtension = updatedExtension;
            this.useYn = useYn;
        }
    }
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
        private List<FixExtensionResDTO.extension> extensionList;

        @Builder
        private extensionList(List<FixExtensionResDTO.extension> extensionList){
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
