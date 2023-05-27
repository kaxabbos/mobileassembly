package com.mobileassembly.cont;

import com.mobileassembly.cont.main.Attributes;
import com.mobileassembly.models.Carts;
import com.mobileassembly.models.Incomes;
import com.mobileassembly.models.Products;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductsCont extends Attributes {

    @GetMapping
    public String products(Model model) {
        AddAttributesProducts(model);
        return "products";
    }

    @GetMapping("/my")
    public String productsMy(Model model) {
        AddAttributesProductsMy(model);
        return "productsMy";
    }

    @GetMapping("/add")
    public String productAdd(Model model) {
        AddAttributesProductAdd(model);
        return "productAdd";
    }

    @GetMapping("/edit/{id}")
    public String productEdit(Model model, @PathVariable Long id) {
        AddAttributesProductEdit(model, id);
        return "productEdit";
    }

    @PostMapping("/buy/{id}")
    public String productBuy(@PathVariable Long id, @RequestParam int count) {
        Products product = repoProducts.getReferenceById(id);
        product.setCount(product.getCount() - count);
        Incomes income = product.getIncome();
        income.setCount(income.getCount() + count);
        income.setIncome(income.getIncome() + (count * product.getPrice()));
        repoProducts.save(product);

        repoCarts.save(new Carts(count, count * product.getPrice(),getUser(),product));

        AddAction("Товар куплен: " + product.getName());
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String productDelete(@PathVariable Long id) {
        Products product = repoProducts.getReferenceById(id);
        AddAction("Товар удален: " + product.getName());
        repoProducts.delete(product);
        return "redirect:/products";
    }

    @PostMapping("/add")
    public String productAdd(Model model, @RequestParam String name, @RequestParam int count, @RequestParam int price, @RequestParam MultipartFile file) {
        String res = "";
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "devices/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Слишком большой размер аватарки");
                AddAttributesProductAdd(model);
                return "productAdd";
            }
        }

        Products product = new Products(name, count, price, res);
        repoProducts.save(product);

        AddAction("Добавлен новый товар: " + product.getName());
        return "redirect:/products/add";
    }

    @PostMapping("/edit/{id}")
    public String productEdit(Model model, @RequestParam String name, @RequestParam int count, @RequestParam int price, @RequestParam MultipartFile file, @PathVariable Long id) {
        Products product = repoProducts.getReferenceById(id);
        product.setName(name);
        product.setCount(count);
        product.setPrice(price);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String res = "";
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "devices/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Слишком большой размер аватарки");
                AddAttributesProductAdd(model);
                return "productAdd";
            }
            product.setFile(res);
        }

        repoProducts.save(product);

        AddAction("Отредактирован товар: " + product.getName());
        return "redirect:/products";
    }
}
