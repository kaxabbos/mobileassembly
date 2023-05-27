package com.mobileassembly.cont.main;

import com.mobileassembly.models.Actions;
import com.mobileassembly.models.Devices;
import com.mobileassembly.models.Users;
import com.mobileassembly.models.enums.DeviceType;
import com.mobileassembly.models.enums.Role;
import com.mobileassembly.models.enums.Select;
import com.mobileassembly.models.enums.Status;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Attributes extends Main {
    protected void AddAttributes(Model model) {
        model.addAttribute("avatar", getAvatar());
        model.addAttribute("firstnameLastname", getFirstnameLastname());
        model.addAttribute("role", getUserRole());
    }

    protected void AddAttributesIndex(Model model) {
        String userRole = getUserRole();
        AddAttributesSearch(model, Status.ALL, DeviceType.ALL);
    }

    protected void AddAttributesProducts(Model model) {
        AddAttributes(model);
        model.addAttribute("products", repoProducts.findAll());
    }

    protected void AddAttributesProductsMy(Model model) {
        AddAttributes(model);
        model.addAttribute("carts", repoCarts.findAll());
    }

    protected void AddAttributesProductAdd(Model model) {
        AddAttributes(model);
    }

    protected void AddAttributesProductEdit(Model model, Long id) {
        AddAttributes(model);
        model.addAttribute("product", repoProducts.getReferenceById(id));
    }

    protected void AddAttributesProfile(Model model) {
        AddAttributes(model);
        Users user = getUser();
        model.addAttribute("user", user);
        model.addAttribute("allDevice", user.getDevices().size());
        model.addAttribute("right", user.getDevices().size());
    }

    protected void AddAttributesProfiles(Model model) {
        AddAttributes(model);
        model.addAttribute("usersList", repoUsers.findAllByOrderByRole());
        model.addAttribute("roles", Arrays.asList(Role.values()));
    }

    protected void AddAttributesService(Model model) {
        AddAttributes(model);
        List<Devices> devices = new ArrayList<>();

        if (getUserRole().equals(Role.TECHNICIAN.name())) {
            devices.addAll(repoDevices.findByStatus(Status.WAITING));
            devices.addAll(repoDevices.findByStatus(Status.ASSEMBLING));
        } else if (getUserRole().equals(Role.TESTER.name())) {
            devices.addAll(repoDevices.findByStatus(Status.TESTED));
        }

        model.addAttribute("devices", devices);
    }

    protected void AddAttributesStats(Model model, Select select, Status status, DeviceType type, Role role) {
        AddAttributes(model);
        if (select == Select.OFFICE_EQUIPMENT) {
            List<Devices> devices;
            if (status == Status.ALL && type == DeviceType.ALL) devices = repoDevices.findAll();
            else if (status == Status.ALL) devices = repoDevices.findByDeviceType(type);
            else if (type == DeviceType.ALL) devices = repoDevices.findByStatus(status);
            else devices = repoDevices.findByStatusAndDeviceType(status, type);
            model.addAttribute("devices", devices);
        } else {
            model.addAttribute("users", repoUsers.findByRole(role));
        }
        model.addAttribute("roles", Role.values());
        model.addAttribute("selects", Select.values());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("types", DeviceType.values());
        model.addAttribute("roleSelected", role);
        model.addAttribute("deviceStatusSelected", status);
        model.addAttribute("deviceTypeSelected", type);
    }

    protected void AddAttributesStatsProducts(Model model) {
        AddAttributes(model);
        model.addAttribute("incomes", repoIncomes.findAll());
    }

    protected void AddAttributesActionsList(Model model) {
        AddAttributes(model);
        model.addAttribute("users", repoUsers.findAllByOrderByRole());
    }

    protected void AddAttributesActions(Model model, Long userId, String date) {
        AddAttributes(model);
        model.addAttribute("user", repoUsers.getReferenceById(userId));
        if (date == null || date.equals("")) {
            date = DateNow();
        }

        List<Actions> list = getUser().getActions();
        String finalDate = date;
        List<Actions> actions = list.stream().filter(action -> action.getDate().startsWith(finalDate)).toList();

        model.addAttribute("actions", actions);
        model.addAttribute("date", date);
    }

    protected void AddAttributesDeviceAdd(Model model) {
        AddAttributes(model);
        model.addAttribute("types", Arrays.asList(DeviceType.values()));
        model.addAttribute("DeviceTypeAll", DeviceType.ALL);
    }

    protected void AddAttributesDeviceEdit(Model model, Long idDevice) {
        AddAttributes(model);
        model.addAttribute("types", Arrays.asList(DeviceType.values()));
        model.addAttribute("device", repoDevices.getReferenceById(idDevice));
        model.addAttribute("DeviceTypeAll", DeviceType.ALL);
    }

    protected void AddAttributesMyDevices(Model model) {
        AddAttributes(model);
        model.addAttribute("devices", getUser().getDevices());
    }

    protected void AddAttributesSearch(Model model, Status status, DeviceType type) {
        AddAttributes(model);
        List<Devices> devices;
        if (status == Status.ALL && type == DeviceType.ALL) devices = repoDevices.findAll();
        else if (status == Status.ALL) devices = repoDevices.findByDeviceType(type);
        else if (type == DeviceType.ALL) devices = repoDevices.findByStatus(status);
        else devices = repoDevices.findByStatusAndDeviceType(status, type);
        model.addAttribute("devices", devices);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("types", DeviceType.values());
        model.addAttribute("deviceStatusSelected", status);
        model.addAttribute("deviceTypeSelected", type);
    }

    protected void AddAttributesSearch(Model model, String search) {
        AddAttributes(model);
        List<Devices> temp = new ArrayList<>();
        for (Devices i : repoDevices.findAll()) if (i.getName().contains(search)) temp.add(i);
        model.addAttribute("devices", temp);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("deviceStatusSelected", Status.ALL);
        model.addAttribute("types", DeviceType.values());
        model.addAttribute("deviceTypeSelected", DeviceType.ALL);
    }
}
