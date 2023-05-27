package com.mobileassembly.cont;

import com.mobileassembly.cont.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginCont extends Attributes {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
