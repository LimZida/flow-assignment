package madras.flow.assignment.extension.custom.entity;

import lombok.*;
import madras.flow.assignment.common.enums.ErrorEnum;
import madras.flow.assignment.common.exception.custom.CustomRequestException;
import madras.flow.assignment.extension.custom.dto.CustomExtensionResDTO;

import java.util.List;

/**
 * title : CustomExtensionEntity
 *
 * description : 커스텀확장자 DAO <=> DB 접근 entitu
 *
 * reference : innerClass로 DTO 관리하기 https://velog.io/@kjyeon1101/Spring
 *
 * author : 임현영
 * date : 2024.06.01
 **/
public class CustomExtensionEntity {
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
    }

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
        private List<CustomExtensionResDTO.extension> extensionList;

        @Builder
        private extensionList(List<CustomExtensionResDTO.extension> extensionList){
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
