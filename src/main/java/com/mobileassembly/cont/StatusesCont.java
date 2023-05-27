package com.mobileassembly.cont;

import com.mobileassembly.cont.main.Attributes;
import com.mobileassembly.models.Devices;
import com.mobileassembly.models.Serves;
import com.mobileassembly.models.enums.Role;
import com.mobileassembly.models.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StatusesCont extends Attributes {

    @PostMapping("/device/{id}/assembling")
    public String assembling(@PathVariable Long id, @RequestParam int cost, @RequestParam int price) {
        Devices device = repoDevices.getReferenceById(id);
        device.setServes(getUser().getServes());
        device.setCost(cost);
        device.setPrice(price);
        device.setStatus(Status.ASSEMBLING);
        repoDevices.save(device);
        AddAction("Начата сборка устройства \"" + device.getName());
        return "redirect:/service";
    }

    @GetMapping("/device/{id}/test")
    public String test(@PathVariable Long id) {
        Devices device = repoDevices.getReferenceById(id);
        device.setServes(null);
        device.setStatus(Status.TESTED);
        repoDevices.save(device);
        AddAction("Устройство собрано \"" + device.getName());
        return "redirect:/service";
    }

    @GetMapping("/device/{id}/complete")
    public String complete(@PathVariable Long id) {
        Devices device = repoDevices.getReferenceById(id);
        device.setStatus(Status.COMPLETE);
        repoDevices.save(device);
        AddAction("Устройство протестировано \"" + device.getName());
        return "redirect:/service";
    }
}
