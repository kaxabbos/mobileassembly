package com.mobileassembly.cont;

import com.mobileassembly.cont.main.Attributes;
import com.mobileassembly.models.Users;
import com.mobileassembly.models.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class RegCont extends Attributes {
    @GetMapping("/reg")
    public String reg() {
        return "reg";
    }

    @PostMapping("/reg")
    public String newUser(Model model, @RequestParam String username, @RequestParam String lastname, @RequestParam String email, @RequestParam String password, @RequestParam String passwordRepeat) {
        if (repoUsers.findAll().isEmpty() || repoUsers.findAll().size() == 0){
            repoUsers.save(new Users(username, username, lastname, email, password, Role.ADMIN, defAvatar));
            return "redirect:/login";
        }

        if (!Objects.equals(password, passwordRepeat)) {
            model.addAttribute("message",
                    "Пароли не совпадают!");
            return "reg";
        }

        Users userFromDB = repoUsers.findByUsernameAndLastname(username, lastname);
        if (userFromDB != null) {
            if (Objects.equals(userFromDB.getEmail(), email)) model.addAttribute("message",
                    "Пользователь c таким же адресом электронной почты уже существует существует!");
            else model.addAttribute("message",
                    "Пользователь c таким именем и фамилией уже существует существует!");
            return "reg";
        }

        repoUsers.save(new Users(username, username, lastname, email, password, Role.USER, defAvatar));

        return "redirect:/login";
    }
}
