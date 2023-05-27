package com.mobileassembly.cont;

import com.mobileassembly.cont.main.Attributes;
import com.mobileassembly.models.Serves;
import com.mobileassembly.models.Users;
import com.mobileassembly.models.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfilesCont extends Attributes {
    @GetMapping("/profiles")
    public String profiles(Model model) {
        AddAttributesProfiles(model);
        return "profiles";
    }

    @PostMapping("/profiles/{id}/edit")
    public String profileEditRole(@PathVariable Long id, @RequestParam Role role) {
        Users user = repoUsers.getReferenceById(id);
        if ((user.getRole() == Role.ADMIN || user.getRole() == Role.USER) && (role == Role.TECHNICIAN || role == Role.TESTER)) {
            user.setServes(new Serves(user));
        } else {
            user.setServes(null);
        }
        user.setRole(role);
        repoUsers.save(user);
        AddAction("Отредактирован пользователь: " + user.getFirstname() + " " + user.getLastname());
        return "redirect:/profiles";
    }

    @GetMapping("/profiles/{id}/delete")
    public String profileDelete(Model model, @PathVariable Long id) {
        if (id == getUser().getId()) {
            model.addAttribute("message", "Вы не можете удалить самого себя");
            AddAttributesProfiles(model);
            return "profiles";
        }

        Users user = repoUsers.getReferenceById(id);
        repoUsers.delete(user);

        AddAction("Пользователь удален: " + user.getFirstname() + " " + user.getLastname());
        return "redirect:/profiles";
    }
}
