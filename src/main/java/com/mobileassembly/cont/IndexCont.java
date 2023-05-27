package com.mobileassembly.cont;

import com.mobileassembly.cont.main.Attributes;
import com.mobileassembly.models.enums.DeviceType;
import com.mobileassembly.models.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexCont extends Attributes {
    @GetMapping("/index")
    public String Index1(Model model) {
        AddAttributesIndex(model);
        return "index";
    }

    @GetMapping("/")
    public String Index2(Model model) {
        AddAttributesIndex(model);
        return "index";
    }

    @PostMapping("/search/status_type")
    String SearchStatusType(Model model, @RequestParam Status status, @RequestParam DeviceType type) {
        AddAttributesSearch(model, status, type);
        return "index";
    }
}
