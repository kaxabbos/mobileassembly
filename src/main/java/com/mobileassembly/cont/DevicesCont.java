package com.mobileassembly.cont;

import com.mobileassembly.cont.main.Attributes;
import com.mobileassembly.models.Devices;
import com.mobileassembly.models.enums.DeviceType;
import com.mobileassembly.models.enums.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Controller
public class DevicesCont extends Attributes {

    @GetMapping("/add")
    public String add(Model model) {
        AddAttributesDeviceAdd(model);
        return "deviceAdd";
    }

    @PostMapping("/device/add")
    public String addDevice(Model model, @RequestParam String name, @RequestParam DeviceType type, @RequestParam String description, @RequestParam MultipartFile file) {
        Devices device = new Devices(name, type, Status.WAITING, getUser());

        if (description == null || description.equals("")) device.setDescription(null);
        else device.setDescription(description);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            String res = "";
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "devices/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Слишком большой размер аватарки");
                AddAttributesDeviceAdd(model);
                return "deviceAdd";
            }
            device.setFile(res);
        } else {
            device.setFile(defDevices.get(type));
        }

        repoDevices.save(device);

        AddAction("Добавлено новое устройство: " + device.getName());
        return "redirect:/myDevices";
    }

    @GetMapping("/device/{id}/edit")
    public String edit(Model model, @PathVariable Long id) {
        AddAttributesDeviceEdit(model, id);
        return "deviceEdit";
    }

    @GetMapping("/device/{id}/edit/default/file")
    public String EditDefaultFile(@PathVariable Long id, Model model) {
        Devices device = repoDevices.getReferenceById(id);

        boolean flag = true;

        for (String i : defDevices.values()) {
            if (device.getFile().equals(i)) {
                flag = false;
                break;
            }
        }

        if (flag) {
            try {
                Files.delete(Paths.get(uploadImg + "/" + device.getFile()));
            } catch (IOException e) {
                model.addAttribute("message", "Не удалось изменить фотографию");
                AddAttributesDeviceEdit(model, id);
                return "deviceEdit";
            }
        }

        device.setFile(defDevices.get(device.getDeviceType()));

        repoDevices.save(device);

        AddAction("Сброс фотографии по умолчанию устройства: " + device.getName());

        return "redirect:/myDevices";
    }

    @PostMapping("/device/{id}/edit")
    public String editDevice(Model model, @PathVariable Long id, @RequestParam String name, @RequestParam DeviceType type, @RequestParam String description, @RequestParam MultipartFile file) {
        Devices device = repoDevices.getReferenceById(id);
        device.setName(name);
        device.setDeviceType(type);
        if (description == null || description.equals("")) device.setDescription(null);
        else device.setDescription(description);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            String res = "";
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "devices/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Слишком большой размер аватарки");
                AddAttributesDeviceEdit(model, id);
                return "deviceEdit";
            }

            boolean flag = true;

            for (String i : defDevices.values()) {
                if (device.getFile().equals(i)) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                try {
                    Files.delete(Paths.get(uploadImg + "/" + device.getFile()));
                } catch (IOException e) {
                    model.addAttribute("message", "Не удалось изменить фотографию");
                    AddAttributesDeviceAdd(model);
                    return "deviceAdd";
                }
            }
            device.setFile(res);
        }

        repoDevices.save(device);
        AddAction("Отредактировано устройство: " + device.getName());
        return "redirect:/myDevices";
    }

    @GetMapping("/device/{id}/delete")
    public String deleteDevice(@PathVariable Long id) {
        Devices device = repoDevices.getReferenceById(id);
        repoDevices.delete(device);
        AddAction("Устройство удалено: " + device.getName());
        return "redirect:/myDevices";
    }
}
