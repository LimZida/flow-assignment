package madras.flow.assignment.extension.fix.service;

import madras.flow.assignment.extension.fix.dto.FixExtensionReqDTO;
import madras.flow.assignment.extension.fix.dto.FixExtensionResDTO;

/**
 * title : FixExtensionService
 *
 * description : FixExtensionService 추상체
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.05.30
 **/
public interface FixExtensionService {
    FixExtensionResDTO.extensionList selectFixExtensionList();
    FixExtensionResDTO.extensionList updateFixExtension(FixExtensionReqDTO.updateInfo updateInfo);
}
