package com.mobileassembly.cont;

import com.mobileassembly.cont.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutCont extends Attributes {

    @GetMapping("/about")
    public String about(Model model) {
        AddAttributes(model);
        return "about";
    }

}
