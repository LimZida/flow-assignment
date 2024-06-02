package madras.flow.assignment.extension.fix.repository;

import madras.flow.assignment.extension.fix.dto.FixExtensionReqDTO;
import madras.flow.assignment.extension.fix.dto.FixExtensionResDTO;
import madras.flow.assignment.extension.fix.entity.FixExtensionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * title : FixExtensionRepository
 *
 * description : FixExtension 쿼리 추상체 => fix-extension.xml
 *               selectFixExtensionList -> 모든 고정확장자 조회
 *               selectFixExtensionUseYn -> 특정 고정확장자 사용여부 조회
 *               updateFixExtension ->  고정확장자 정보수정
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.05.31
 **/
@Repository
@Mapper
public interface FixExtensionRepository {
    List<FixExtensionEntity.extension> selectFixExtensionList();
    FixExtensionEntity.usage selectFixExtensionUseYn(FixExtensionEntity.updateInfo updateInfo);
    Integer updateFixExtension(FixExtensionEntity.updateInfo updateInfo);
}
