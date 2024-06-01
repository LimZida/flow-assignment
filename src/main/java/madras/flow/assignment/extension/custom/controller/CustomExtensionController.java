package madras.flow.assignment.extension.custom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import madras.flow.assignment.extension.custom.dto.CustomExtensionReqDTO;
import madras.flow.assignment.extension.custom.dto.CustomExtensionResDTO;
import madras.flow.assignment.extension.custom.service.CustomExtensionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * title : CustomExtensionController
 *
 * description : 커스텀 확장자 Controller
 *
 * reference :
 *
 *
 * author : 임현영
 *
 * date : 2024.05.30
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/flow/assignment/custom-extensions")
public class CustomExtensionController {
    private final CustomExtensionService customExtensionService;

    //커스텀 확장자 조회
    @GetMapping("")
    public ResponseEntity<Object> selectCustomExtensionList(){
        CustomExtensionResDTO.extensionList extensionList = customExtensionService.selectCustomExtensionList();

        return ResponseEntity.status(HttpStatus.OK).body(extensionList);
    }

    //커스텀 확장자 추가
    @PostMapping("")
    public ResponseEntity<Object> insertCustomExtension(@RequestBody CustomExtensionReqDTO.insertInfo insertInfo){
        //요청값 검증
        insertInfo.validate();

        CustomExtensionResDTO.extensionList extensionList = customExtensionService.insertCustomExtension(insertInfo);

        return ResponseEntity.status(HttpStatus.OK)
                .body(extensionList);
    }

    //커스텀 확장자 삭제
    @DeleteMapping("")
    public ResponseEntity<Object> deleteCustomExtension(@RequestBody CustomExtensionReqDTO.deleteInfo deleteInfo){
        //요청값 검증
        deleteInfo.validate();

        CustomExtensionResDTO.extensionList extensionList = customExtensionService.deleteCustomExtension(deleteInfo);

        return ResponseEntity.status(HttpStatus.OK)
                .body(extensionList);
    }

}
