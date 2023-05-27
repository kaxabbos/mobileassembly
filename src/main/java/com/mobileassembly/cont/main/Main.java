package com.mobileassembly.cont.main;

import com.mobileassembly.models.Actions;
import com.mobileassembly.models.Users;
import com.mobileassembly.models.enums.DeviceType;
import com.mobileassembly.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Main {
    @Autowired
    protected RepoUsers repoUsers;
    @Autowired
    protected RepoDevices repoDevices;
    @Autowired
    protected RepoActions repoActions;
    @Autowired
    protected RepoCarts repoCarts;
    @Autowired
    protected RepoIncomes repoIncomes;
    @Autowired
    protected RepoProducts repoProducts;
    @Autowired
    protected RepoServes repoServes;

    @Value("${upload.img}")
    protected String uploadImg;

    protected Map<DeviceType, String> defDevices = new HashMap<>();

    {
        defDevices.put(DeviceType.SENSORY, "default/phone.png");
        defDevices.put(DeviceType.FOLDING, "default/laptop.png");
        defDevices.put(DeviceType.PUSH_BUTTON, "default/pc.png");
        defDevices.put(DeviceType.CURVED, "default/tablet.png");
    }

    protected String defAvatar = "default/avatar.png";

    protected Users getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return repoUsers.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    protected String getUserRole() {
        Users user = getUser();
        if (user != null) return String.valueOf(user.getRole());
        return "NOT";
    }

    protected Long getUserID() {
        Users user = getUser();
        if (user != null) return user.getId();
        return 0L;
    }

    protected String getAvatar() {
        Users user = getUser();
        if (user != null) return user.getAvatar();
        return defAvatar;
    }

    protected String getFirstnameLastname() {
        Users user = getUser();
        if (user != null) return user.getFirstname() + " " + user.getLastname();
        return "Добро пожаловать";
    }

    protected String DateAndTimeNow() {
        String date = LocalDateTime.now().toString();
        return date.substring(0, 10) + " " + date.substring(11, 19);
    }

    protected String DateNow() {
        return LocalDateTime.now().toString().substring(0, 10);
    }

    protected void AddAction(String action) {
        repoActions.save(new Actions(action, DateAndTimeNow(), getUser()));
    }
}
