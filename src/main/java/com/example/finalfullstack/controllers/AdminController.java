package com.example.finalfullstack.controllers;

import com.example.finalfullstack.models.*;
import com.example.finalfullstack.repositories.*;
import com.example.finalfullstack.services.*;
import com.example.finalfullstack.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminController {

    private final PersonService personService;
    private final ProductService productService;
    private final OrderService orderService;
    private final ProductOrderService productOrderService;
    private final CategoryService categoryService;


    @Value("${upload.path}")
    private String uploadPath;

    public AdminController(PersonService personService, ProductService productService, ProductOrderService productOrderService, CategoryService categoryService, OrderService orderService) {
        this.personService = personService;
        this.productService = productService;
        this.productOrderService = productOrderService;
        this.categoryService = categoryService;
        this.orderService = orderService;
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one")MultipartFile file_one, @RequestParam("file_two")MultipartFile file_two, @RequestParam("file_three")MultipartFile file_three, @RequestParam("file_four")MultipartFile file_four, @RequestParam("file_five")MultipartFile file_five, @RequestParam("category")int category, Model model) throws IOException {

        if (bindingResult.hasErrors()){
            model.addAttribute("category", categoryService.getAll());
            return "admin/add_product";
        }
        Category category_db = categoryService.getById(category);

        uploadFileImage(product, file_one);

        uploadFileImage(product, file_two);
        uploadFileImage(product, file_three);
        uploadFileImage(product, file_four);
        uploadFileImage(product, file_five);

        productService.saveProduct(product, category_db);

        return "redirect:/admin";

    }

    private void uploadFileImage(Product product, MultipartFile file_one) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()){
            uploadDir.mkdir();
        }
        if (!file_one.getOriginalFilename().equals("")){
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
    }

    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "admin/admin";
    }

    @GetMapping("/admin/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryService.getAll());
        return "admin/add_product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProductById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id){

        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("category", categoryService.getAll());
        return "admin/edit_product";
    }

    @PostMapping("/admin/product/edit/{id}")
    public String editProduct(@ModelAttribute("product")@Valid Product product, BindingResult bindingResult, @PathVariable("id") int id, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("category", categoryService.getAll());
            return "admin/edit_product";
        }
        productService.updateProductById(product, id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/order")
    public String order(@RequestParam(value = "search", defaultValue = "") String search, Model model){

        model.addAttribute("search_order", orderService.getByNumberEndingWith(search));

        List<ProductOrder> productOrderList = productOrderService.getAll();
        List<Product> productList = productService.getAllProduct();
        List<Person> personList = personService.getAll();

        model.addAttribute("users", personList);
        model.addAttribute("products", productList);
        model.addAttribute("productOrders", productOrderList);
        model.addAttribute("value_search", "");
        return "admin/order";
    }

    @PostMapping("/admin/order/status_upgrade/{id}")
    public String orderUpgradeStatus(@PathVariable("id") int id, @RequestParam(value = "search", defaultValue = "") String search, Model model){
        Order order = orderService.getOrderById(id);
        orderService.updateProductById(order, id);
        model.addAttribute("search_order", orderService.getByNumberEndingWith(search));

        List<ProductOrder> productOrderList = productOrderService.getAll();
        List<Product> productList = productService.getAllProduct();
        List<Person> personList = personService.getAll();

        model.addAttribute("users", personList);
        model.addAttribute("products", productList);
        model.addAttribute("productOrders", productOrderList);
        model.addAttribute("value_search", search);
        return "admin/order";
    }

    @PostMapping("/admin/order/status_remove/{id}")
    public String orderRemoveStatus(@PathVariable("id") int id, @RequestParam(value = "search", defaultValue = "") String search, Model model){
        Order order = orderService.getOrderById(id);
        orderService.stopProductById(order, id);
        model.addAttribute("search_order", orderService.getByNumberEndingWith(search));

        List<ProductOrder> productOrderList = productOrderService.getAll();
        List<Product> productList = productService.getAllProduct();
        List<Person> personList = personService.getAll();

        model.addAttribute("users", personList);
        model.addAttribute("products", productList);
        model.addAttribute("productOrders", productOrderList);
        model.addAttribute("value_search", search);
        return "admin/order";
    }

    @PostMapping("/admin/order")
    public String orderSearch(@RequestParam(value = "search", defaultValue = "") String search, Model model){
        model.addAttribute("search_order", orderService.getByNumberEndingWith(search));

        List<ProductOrder> productOrderList = productOrderService.getAll();
        List<Product> productList = productService.getAllProduct();
        List<Person> personList = personService.getAll();

        model.addAttribute("users", personList);
        model.addAttribute("products", productList);
        model.addAttribute("productOrders", productOrderList);
        model.addAttribute("value_search", search);
        return "admin/order";
    }

    @GetMapping("/admin/person")
    public String users(Model model){
        model.addAttribute("users", personService.getAll());
        return "admin/user";
    }

    @PostMapping("/admin/person/upcast/{id}")
    public String upgradePerson(@PathVariable("id") int id){
        Person person = personService.getPersonById(id);
        personService.upgradePersonById(person, id);
        return "redirect:/admin/person";
    }

    @PostMapping("/admin/person/downcast/{id}")
    public String downgradePerson(@PathVariable("id") int id){
        Person person = personService.getPersonById(id);
        personService.downgradePersonById(person, id);
        return "redirect:/admin/person";
    }
}
