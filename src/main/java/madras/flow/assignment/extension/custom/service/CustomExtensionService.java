package madras.flow.assignment.extension.custom.service;

import madras.flow.assignment.extension.custom.dto.CustomExtensionReqDTO;
import madras.flow.assignment.extension.custom.dto.CustomExtensionResDTO;
/**
 * title : CustomExtensionService
 *
 * description : CustomExtension Service 추상체
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.05.30
 **/
public interface CustomExtensionService {
    CustomExtensionResDTO.extensionList selectCustomExtensionList();
    CustomExtensionResDTO.extensionList insertCustomExtension(CustomExtensionReqDTO.insertInfo insertInfo);
    CustomExtensionResDTO.extensionList deleteCustomExtension(CustomExtensionReqDTO.deleteInfo deleteInfo);
}
