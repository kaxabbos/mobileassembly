package com.mobileassembly.cont;

import com.mobileassembly.cont.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ActionsCont extends Attributes {

    @GetMapping("/actions")
    public String ActionsList(Model model) {
        AddAttributesActionsList(model);
        return "actionsList";
    }

    @GetMapping("/actions/{userId}")
    public String Actions(Model model, @PathVariable Long userId) {
        AddAttributesActions(model, userId, null);
        return "actions";
    }

    @PostMapping("/search/{userId}/actions")
    public String SearchActions(Model model, @RequestParam String date, @PathVariable String userId) {
        AddAttributesActions(model, Long.parseLong(userId), date);
        return "actions";
    }

}
