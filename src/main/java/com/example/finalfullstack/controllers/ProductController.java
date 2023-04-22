package com.example.finalfullstack.controllers;

import com.example.finalfullstack.models.Category;
import com.example.finalfullstack.repositories.CategoryRepository;
import com.example.finalfullstack.repositories.ProductRepository;
import com.example.finalfullstack.services.CategoryService;
import com.example.finalfullstack.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/product")
    public String getAllProducts(@RequestParam(name = "search", required = false, defaultValue = "") String search, @RequestParam(name = "ot", required = false, defaultValue = "") String ot, @RequestParam(name = "do", required = false, defaultValue = "") String dO, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "contract", required = false, defaultValue = "") String contract, Model model) {
        model.addAttribute("sort_product", productService.getByTitle(search));
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("value_price", price);
        model.addAttribute("value_contract", contract);
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", dO);
        model.addAttribute("value_search", search);
        return "product/product";
    }

    @PostMapping("/product")
    public String productSpecific(@RequestParam(name = "search", required = false, defaultValue = "") String search, @RequestParam(name = "ot", required = false, defaultValue = "") String ot, @RequestParam(name = "do", required = false, defaultValue = "") String dO, @RequestParam(name = "price", defaultValue = "sorted_by_ascending_price") String price, @RequestParam(value = "contract", required = false, defaultValue = "null_category") String contract, Model model) {

        if (ot.equals("")) ot = "0";
        if (Integer.parseInt(ot) < 0) ot = "0";
        if (dO.equals("")) dO = "100000";
        if (Integer.parseInt(dO) < 0 || Integer.parseInt(dO) > 100000) dO = "100000";

        List<Category> categoryList = categoryService.getAll();
        if (price.equals("sorted_by_ascending_price")) {
            //проверяем на отсутствие категории
            if (contract.equals("null_category")) {
                model.addAttribute("sort_product", productService.getByPriceAsc(search, ot, dO));
            }
            //пробегаемся по категориям
            for (Category category : categoryList){
                if (contract.equals(category.getName())){
                    model.addAttribute("sort_product", productService.getByCategoryAndPriceAsc(search, ot, dO, category.getId()));
                    break;
                }
            }
        }
        if (price.equals("sorted_by_descending_price")) {
            //проверяем на отсутствие категории
            if (contract.equals("null_category")) {
                model.addAttribute("sort_product", productService.getByPriceDesc(search, ot, dO));
            }
            //пробегаемся по категориям
            for (Category category : categoryList){
                if (contract.equals(category.getName())){
                    model.addAttribute("sort_product", productService.getByCategoryAndPriceDesc(search, ot, dO, category.getId()));
                }
            }
        }

        if (ot.equals("0")) ot = "";
        if (dO.equals("100000")) dO = "";

        model.addAttribute("value_price", price);
        model.addAttribute("value_contract", contract);
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", dO);
        model.addAttribute("value_search", search);
        return "product/product";
    }

    @GetMapping("/product/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product/info_product";
    }
}
