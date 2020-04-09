package pl.makuch.springsecurityjwt.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {


    @GetMapping("/permitAll")
    public String permitAll() {
        return "welcome all ";
    }

    @GetMapping("/permitUser")
    public String permitUser() {
        return "welcome user";
    }

    @GetMapping("/permitAdmin")
    public String permitAdmin() {
        return "welcome admin";
    }

}
