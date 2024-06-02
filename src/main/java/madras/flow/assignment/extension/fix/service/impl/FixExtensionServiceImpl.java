package madras.flow.assignment.extension.fix.service.impl;

import lombok.RequiredArgsConstructor;
import madras.flow.assignment.common.enums.ErrorEnum;
import madras.flow.assignment.common.exception.custom.CustomDBException;
import madras.flow.assignment.common.exception.custom.CustomServiceException;
import madras.flow.assignment.extension.custom.dto.CustomExtensionReqDTO;
import madras.flow.assignment.extension.custom.dto.CustomExtensionResDTO;
import madras.flow.assignment.extension.custom.entity.CustomExtensionEntity;
import madras.flow.assignment.extension.fix.dto.FixExtensionReqDTO;
import madras.flow.assignment.extension.fix.dto.FixExtensionResDTO;
import madras.flow.assignment.extension.fix.entity.FixExtensionEntity;
import madras.flow.assignment.extension.fix.repository.FixExtensionRepository;
import madras.flow.assignment.extension.fix.service.FixExtensionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * title : FixExtensionServiceImpl
 *
 * description : FixExtensionServiceImpl 구현체
 *
 * reference : DTO의 적용 범위 https://tecoble.techcourse.co.kr/post/2021-04-25-dto-layer-scope/
 *
 * author : 임현영
 * date : 2024.05.30
 **/
@Service
@RequiredArgsConstructor
public class FixExtensionServiceImpl implements FixExtensionService {
    private final FixExtensionRepository fixExtensionRepository;

    // 고정 확장자 조회
    /*
    * 1. 사용여부에 상관없이 전체 리스트 조회 진행한다.
    * */
    @Transactional(readOnly = true)
    @Override
    public FixExtensionResDTO.extensionList selectFixExtensionList() {
        List<FixExtensionResDTO.extension> fixExtensionList = getExtensionList();

        return FixExtensionResDTO.extensionList.builder()
                .extensionList(fixExtensionList)
                .build();
    }

    // 고정 확장자 미사용 사용 수정
    /*
    * 1. 요청받은 고정 타입의 원래 사용 state와 변경하려는 사용 state가 같으면 CustomServiceException 예외
    *
    * 2. 고정 확장자의 경우, 사용 안함 처리 되어도 정보가 남아있어야 하므로 update 진행
    * 2-1. 업데이트 오류 시 -> CustomDBException
    * 2-2. 업데이트 성공 시 다음 로직
    *
    * 3. List 조회 진행
    *
    * */
    @Transactional
    @Override
    public FixExtensionResDTO.extensionList updateFixExtension(FixExtensionReqDTO.updateInfo updateInfo) {
        // 정보 수정 검증
        validateUpdateFixExtension(updateInfo);

        // 정보 수정 진행
        getUpdateFixExtension(updateInfo);

        // 리스트 응답
        List<FixExtensionResDTO.extension> fixExtensionList = getExtensionList();

        return FixExtensionResDTO.extensionList.builder()
                .extensionList(fixExtensionList)
                .build();
    }

    /*
    * FixExtension 내부 로직에 사용되는 함수
    * */

    // 고정 확장자 정보 수정 함수
    private void getUpdateFixExtension(FixExtensionReqDTO.updateInfo updateInfo){
        // 수정 진행
        Integer updateFixExtension = fixExtensionRepository.updateFixExtension(updateMapping(updateInfo));
        // DB 업데이트 오류
        if (updateFixExtension == null){
            throw new CustomDBException(ErrorEnum.DATA04.name(),ErrorEnum.DATA04.getValue(),null);
        }

        // 업데이트 결과 없다면 예외
        if (updateFixExtension < 1){
            throw new CustomDBException(ErrorEnum.FEU01.name(),ErrorEnum.FEU01.getValue(),null);
        }
    }

    // 고정확장자 정보 수정 검증 함수
    private void validateUpdateFixExtension(FixExtensionReqDTO.updateInfo updateInfo){
        // 기존 사용정보 DB에서 조회
        FixExtensionEntity.usage fixExtension = fixExtensionRepository.selectFixExtensionUseYn(updateMapping(updateInfo));

        // 변경 사용 상태
        String changedState = updateInfo.getUseYn();
        // 기존 사용 상태
        String originalState = fixExtension.getExtensionUsage();

        // 변경하고자 하는 상태와 실제 DB값이 동일하면 Update 예외
        if(changedState.equals(originalState)){
            throw new CustomServiceException(ErrorEnum.FEU02.name(),ErrorEnum.FEU02.getValue(),null);
        }
    }

    // 업데이트 dto <=> Entity 매핑
    private FixExtensionEntity.updateInfo updateMapping(FixExtensionReqDTO.updateInfo insertInfo){
        return FixExtensionEntity.updateInfo.builder()
                .updatedExtension(insertInfo.getUpdatedExtension())
                .useYn(insertInfo.getUseYn())
                .build();
    }

    //리스트 조회
    private List<FixExtensionResDTO.extension> getExtensionList(){
        List<FixExtensionEntity.extension> extensionList = fixExtensionRepository.selectFixExtensionList();
        // DB 조회 오류 시 예외
        if (extensionList == null){
            throw new CustomDBException(ErrorEnum.FER01.name(),ErrorEnum.FER01.getValue(),null);
        }

        return selectMapping(extensionList);
    }

    // 조회 리스트 Entity <=> DTO 변환
    private List<FixExtensionResDTO.extension> selectMapping(List<FixExtensionEntity.extension> extensionList) {
        return extensionList.stream().map(entity ->
                        FixExtensionResDTO.extension.builder()
                        .extensionName(entity.getExtensionName())
                        .extensionUsage(entity.getExtensionUsage())
                        .build())
                        .collect(Collectors.toList());
    }
}
