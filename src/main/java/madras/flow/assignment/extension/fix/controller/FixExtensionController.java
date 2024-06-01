package madras.flow.assignment.extension.fix.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import madras.flow.assignment.extension.fix.dto.FixExtensionReqDTO;
import madras.flow.assignment.extension.fix.dto.FixExtensionResDTO;
import madras.flow.assignment.extension.fix.service.FixExtensionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * title : FixExtensionController
 *
 * description : 고정 확장자 Controller
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
@RequestMapping("/flow/assignment/fix-extensions")
public class FixExtensionController {
    private final FixExtensionService fixExtensionService;

    //고정 확장자 조회
    @GetMapping("")
    public ResponseEntity<Object> selectCustomExtensionList(){
        FixExtensionResDTO.extensionList extensionList = fixExtensionService.selectFixExtensionList();

        return ResponseEntity.status(HttpStatus.OK).body(extensionList);
    }

    //고정 확장자 미사용, 사용 요청
    @PutMapping("")
    public ResponseEntity<Object> updateCustomExtension(@RequestBody FixExtensionReqDTO.updateInfo updateInfo){
        //요청값 검증
        updateInfo.validate();

        FixExtensionResDTO.extensionList extensionList = fixExtensionService.updateFixExtension(updateInfo);

        return ResponseEntity.status(HttpStatus.OK)
                .body(extensionList);
    }
}
