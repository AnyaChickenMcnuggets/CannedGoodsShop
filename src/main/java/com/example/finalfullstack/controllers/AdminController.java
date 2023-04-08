package com.example.finalfullstack.controllers;

import com.example.finalfullstack.models.Category;
import com.example.finalfullstack.models.Image;
import com.example.finalfullstack.models.Product;
import com.example.finalfullstack.repositories.CategoryRepository;
import com.example.finalfullstack.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AdminController {

    private final ProductService productService;

    @Value("${upload.path}")
    private String uploadPath;
    private final CategoryRepository categoryRepository;

    public AdminController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one")MultipartFile file_one, @RequestParam("file_two")MultipartFile file_two, @RequestParam("file_three")MultipartFile file_three, @RequestParam("file_four")MultipartFile file_four, @RequestParam("file_five")MultipartFile file_five, @RequestParam("category")int category, Model model) throws IOException {
        Category category_db = (Category) categoryRepository.findById(category).orElseThrow();

        if (bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "product/add_product";
        }
        if (file_one!=null){
            uploadFileImage(product, file_one);
        }
        if (file_two!=null){
            uploadFileImage(product, file_two);
        }
        if (file_three!=null){
            uploadFileImage(product, file_three);
        }
        if (file_four!=null){
            uploadFileImage(product, file_four);
        }
        if (file_five!=null){
            uploadFileImage(product, file_five);
        }

        productService.saveProduct(product, category_db);

        return "redirect:/admin";

    }

    private void uploadFileImage(Product product, MultipartFile file_one) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()){
            uploadDir.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
        file_one.transferTo(new File(uploadPath + "/" + resultFileName));
        Image image = new Image();
        image.setProduct(product);
        image.setFileName(resultFileName);
        product.addImageToProduct(image);
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/admin/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/add_product";
    }


}
