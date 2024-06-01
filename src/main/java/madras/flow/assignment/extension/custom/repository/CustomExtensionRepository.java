package madras.flow.assignment.extension.custom.repository;

import madras.flow.assignment.extension.custom.dto.CustomExtensionReqDTO;
import madras.flow.assignment.extension.custom.dto.CustomExtensionResDTO;
import madras.flow.assignment.extension.custom.entity.CustomExtensionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * title : CustomExtensionRepository
 *
 * description : CustomExtension 쿼리 추상체 => custom-extension.xml
 *               selectCustomExtensionList -> 모든 커스텀확장자 조회
 *               countCustomExtensionList -> 모든 커스텀확장자 개수 조회
 *               isRequestExtensionExist -> 커스텀확장자 존재여부 확인
 *               insertCustomExtension -> 커스텀확장자 등록
 *               deleteCustomExtension -> 커스텀확장자 삭제
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.05.31
 **/
@Repository
@Mapper
public interface CustomExtensionRepository {
    List<CustomExtensionEntity.extension> selectCustomExtensionList();
    Integer countCustomExtensionList();
    Integer isRequestExtensionExist(CustomExtensionEntity.insertInfo insertInfo);
    Integer insertCustomExtension(CustomExtensionEntity.insertInfo insertInfo);
    Integer deleteCustomExtension(CustomExtensionEntity.deleteInfo deleteInfo);
}
