package com.mobileassembly.cont;

import com.mobileassembly.cont.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyDevicesCont extends Attributes {
    @GetMapping("/myDevices")
    public String MyDevice(Model model) {
        AddAttributesMyDevices(model);
        return "myDevices";
    }
}
