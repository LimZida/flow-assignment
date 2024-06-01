package madras.flow.assignment.welcome.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * title : WelcomeController
 *
 * description : 웰컴페이지
 *
 * reference :
 *
 * author : 임현영
 * date : 2024.05.31
 **/
@RestController
@RequestMapping("/flow")
public class WelcomeController {

    @GetMapping("")
    public String WelcomePage(){
        return "Welcome Json";
    }
}

