package com.mobileassembly.cont;

import com.mobileassembly.cont.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statsProducts")
public class StatsProductsCont extends Attributes {

    @GetMapping
    public String StatsProducts(Model model) {
        AddAttributesStatsProducts(model);
        return "statsProducts";
    }
}
