package madras.flow.assignment.extension.custom.service.impl;

import lombok.RequiredArgsConstructor;
import madras.flow.assignment.common.enums.ErrorEnum;
import madras.flow.assignment.common.exception.custom.CustomDBException;
import madras.flow.assignment.common.exception.custom.CustomServiceException;
import madras.flow.assignment.extension.custom.dto.CustomExtensionReqDTO;
import madras.flow.assignment.extension.custom.dto.CustomExtensionResDTO;
import madras.flow.assignment.extension.custom.entity.CustomExtensionEntity;
import madras.flow.assignment.extension.custom.repository.CustomExtensionRepository;
import madras.flow.assignment.extension.custom.service.CustomExtensionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * title : CustomExtensionService
 *
 * description : CustomExtension Service 구현체
 *
 * reference : DTO의 적용 범위 https://tecoble.techcourse.co.kr/post/2021-04-25-dto-layer-scope/
 *
 * author : 임현영
 * date : 2024.05.30
 **/
@Service
@RequiredArgsConstructor
public class CustomExtensionServiceImpl implements CustomExtensionService {
    private final CustomExtensionRepository customExtensionRepository;

    // 커스텀 확장자 리스트 조회
    /*
    * 1. 커스텀 확장자 전체 리스트를 조회한다.
    * */
    @Transactional(readOnly = true)
    @Override
    public CustomExtensionResDTO.extensionList selectCustomExtensionList() {
        List<CustomExtensionResDTO.extension> customExtensionList = getExtensionList();


        return CustomExtensionResDTO.extensionList.builder()
                .extensionList(customExtensionList)
                .build();
    }

    // 커스텀 확장자 등록
    /*
    *
    * 1. 해당 확장자가 기존 커스텀, 고정 확장자 테이블에 있는지 조회한다.
    * 1-1. 확장자가 존재하면 "이미 존재하는 확장자입니다." CustomServiceException 예외 메세지
    * 1-2. 확장자가 존재하지 않으면 다음 로직 진행
    *
    * 2. 커스텀 확장자 사이즈를 조회한다. (동기처리)
    * 2-1. 사이즈가 200개 이상일 시 CustomServiceException
    * 2-2. 사이즈가 200개 미만일 시 로직 진행
    *
    * 3. 등록 후 리스트 조회
    * */
    @Transactional
    @Override
    public CustomExtensionResDTO.extensionList insertCustomExtension(CustomExtensionReqDTO.insertInfo insertInfo) {
        // 해당 확장자가 기존 커스텀, 고정 확장자 테이블에 있는지 조회한다. (동기처리)
        validateExtensionExist(insertInfo);

        // 커스텀 확장자 사이즈를 조회한다. (동기처리)
        validateCustomExtensionSize();

        // 커스텀 확장자 등록
        getInsertCustomExtension(insertInfo);

        // 리스트 조회
        List<CustomExtensionResDTO.extension> customExtensionList = getExtensionList();


        return CustomExtensionResDTO.extensionList.builder()
                .extensionList(customExtensionList)
                .build();
    }

    // 커스텀 확장자 삭제
    /*
    * 1. 요청받은 커스텀 확장자 DELETE 진행
    * 1-1. Delete 오류 시 -> CustomDBException
    * 1-2. Delte 정상 시 다음 로직 진행
    *
    * 2. Trigger를 통해 delete된 정보 따로 백업한다.
    *
    * 3. 현재 커스텀 리스트 조회
    * */
    @Transactional
    @Override
    public CustomExtensionResDTO.extensionList deleteCustomExtension(CustomExtensionReqDTO.deleteInfo deleteInfo) {
        // Delete 진행
        getDeleteCustomExtension(deleteInfo);

        // 리스트 조회
        List<CustomExtensionResDTO.extension> customExtensionList = getExtensionList();


        return CustomExtensionResDTO.extensionList.builder()
                .extensionList(customExtensionList)
                .build();
    }

    /*
    * CustomExtension 내부 로직에 사용되는 함수
    * */

    // 확장자 조회여부 함수
    private synchronized void validateExtensionExist(CustomExtensionReqDTO.insertInfo insertInfo){
        Integer extensionExist = customExtensionRepository.isRequestExtensionExist(insertMapping(insertInfo));
        if(extensionExist == null){
            throw new CustomServiceException(ErrorEnum.DATA01.name(),ErrorEnum.DATA01.getValue(),null);
        }

        if (extensionExist > 0){
            throw new CustomServiceException(ErrorEnum.CEC02.name(),ErrorEnum.CEC02.getValue(),null);
        }
    }

    // 사이즈 조회 함수
    private synchronized void validateCustomExtensionSize(){
        Integer customExtensionSize = customExtensionRepository.countCustomExtensionList();
        if(customExtensionSize == null){
            throw new CustomServiceException(ErrorEnum.DATA02.name(),ErrorEnum.DATA02.getValue(),null);
        }

        if (customExtensionSize > 200){
            throw new CustomServiceException(ErrorEnum.CEC05.name(),ErrorEnum.CEC05.getValue(),null);
        }
    }

    // 등록 및 검증 함수
    private void  getInsertCustomExtension(CustomExtensionReqDTO.insertInfo insertInfo){
        Integer insertCustomExtension = customExtensionRepository.insertCustomExtension(insertMapping(insertInfo));
        if (insertCustomExtension == null ){
            throw new CustomDBException(ErrorEnum.DATA03.name(),ErrorEnum.DATA03.getValue(),null);
        }

        if (insertCustomExtension < 1){
            throw new CustomDBException(ErrorEnum.CEC01.name(),ErrorEnum.CEC01.getValue(),null);
        }
    }

    // 삽입 dto <=> Entity 매핑
    private CustomExtensionEntity.insertInfo insertMapping(CustomExtensionReqDTO.insertInfo insertInfo){
        return CustomExtensionEntity.insertInfo.builder()
                .insertedExtension(insertInfo.getInsertedExtension())
                .build();
    }


    // 삭제 및 검증 함수
    private void getDeleteCustomExtension(CustomExtensionReqDTO.deleteInfo deleteInfo){
        Integer deleteCustomExtension = customExtensionRepository.deleteCustomExtension(deleteMapping(deleteInfo));
        if (deleteCustomExtension == null){
            throw new CustomDBException(ErrorEnum.DATA05.name(),ErrorEnum.DATA05.getValue(),null);
        }

        if ( deleteCustomExtension < 1){
            throw new CustomDBException(ErrorEnum.CED01.name(),ErrorEnum.CED01.getValue(),null);
        }
    }

    // 삭제 dto <=> Entity 매핑
    private CustomExtensionEntity.deleteInfo deleteMapping(CustomExtensionReqDTO.deleteInfo deleteInfo){
        return CustomExtensionEntity.deleteInfo.builder()
                .deletedExtension(deleteInfo.getDeletedExtension())
                .build();
    }

    // 조회 함수
    private List<CustomExtensionResDTO.extension> getExtensionList(){
        List<CustomExtensionEntity.extension> extensionList = customExtensionRepository.selectCustomExtensionList();
        if (extensionList == null){
            throw new CustomDBException(ErrorEnum.CER01.name(),ErrorEnum.CER01.getValue(),null);
        }

        return selectMapping(extensionList);
    }

    // 조회 리스트 Entity <=> DTO 변환
    private List<CustomExtensionResDTO.extension> selectMapping(List<CustomExtensionEntity.extension> extensionList) {
        return extensionList.stream().map(entity ->
                        CustomExtensionResDTO.extension.builder()
                        .extensionName(entity.getExtensionName())
                        .build())
                        .collect(Collectors.toList());
    }
}
