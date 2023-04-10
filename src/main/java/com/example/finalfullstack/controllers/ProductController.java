package com.example.finalfullstack.controllers;

import com.example.finalfullstack.repositories.ProductRepository;
import com.example.finalfullstack.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("/product")
    public String getAllProducts(@RequestParam(name = "search", required = false, defaultValue = "") String search, @RequestParam(name = "ot", required = false, defaultValue = "") String ot, @RequestParam(name = "do", required = false, defaultValue = "") String dO, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "contract", required = false, defaultValue = "") String contract, Model model) {
        model.addAttribute("sort_product", productRepository.findByTitleContainingIgnoreCase(search));
        model.addAttribute("products", productService.getAllProduct());
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

    @PostMapping("/product")
    public String productSpecific(@RequestParam(name = "search", required = false, defaultValue = "") String search, @RequestParam(name = "ot", required = false, defaultValue = "") String ot, @RequestParam(name = "do", required = false, defaultValue = "") String dO, @RequestParam(name = "price", defaultValue = "sorted_by_ascending_price") String price, @RequestParam(value = "contract", required = false, defaultValue = "null_category") String contract, Model model) {
        model.addAttribute("sort_product", productRepository.findByTitleContainingIgnoreCase(search));

        if (ot.equals("")) ot = "0";
        if (Float.parseFloat(ot) < 0) ot = "0";
        if (dO.equals("")) dO = "100000";
        if (Float.parseFloat(dO) < 0 || Float.parseFloat(dO) > 100000) dO = "100000";

        //TODO 1 проверка asc desc
        if (price.equals("sorted_by_ascending_price")) {
            if (contract.equals("null_category")) {
                model.addAttribute("sort_product", productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(search, Float.parseFloat(ot), Float.parseFloat(dO)));
            }
            if (contract.equals("furniture")) {
                model.addAttribute("sort_product", productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualAndCategory(search, Float.parseFloat(ot), Float.parseFloat(dO), 1));
            }
            if (contract.equals("clothes")) {
                model.addAttribute("sort_product", productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualAndCategory(search, Float.parseFloat(ot), Float.parseFloat(dO), 2));
            }
            if (contract.equals("appliances")) {
                model.addAttribute("sort_product", productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualAndCategory(search, Float.parseFloat(ot), Float.parseFloat(dO), 3));
            }

        }
        if (price.equals("sorted_by_descending_price")) {
            if (contract.equals("null_category")) {
                model.addAttribute("sort_product", productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualDesc(search, Float.parseFloat(ot), Float.parseFloat(dO)));
            }
            if (contract.equals("furniture")) {
                model.addAttribute("sort_product", productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualAndCategoryDesc(search, Float.parseFloat(ot), Float.parseFloat(dO), 1));
            }
            if (contract.equals("clothes")) {
                model.addAttribute("sort_product", productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualAndCategoryDesc(search, Float.parseFloat(ot), Float.parseFloat(dO), 2));
            }
            if (contract.equals("appliances")) {
                model.addAttribute("sort_product", productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualAndCategoryDesc(search, Float.parseFloat(ot), Float.parseFloat(dO), 3));
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

//    @PostMapping("/product/sort")
//    public String productSort(@RequestParam("ot") String ot, @RequestParam("do") String dO, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "contract", required = false, defaultValue = "") String contract, Model model){
//        model.addAttribute("products", productService.getAllProduct());
//
//        if (!ot.isEmpty() & !dO.isEmpty()){
//            if (!price.isEmpty()){
//                if (price.equals("sorted_by_ascending_price")){
//                    if (!contract.isEmpty()){
//                        if (contract.equals("furniture")){
//                            model.addAttribute("sort_product", productRepository.findByCategoryOrderByPriceAsc(Float.parseFloat(ot), Float.parseFloat(dO), 1));
//                        } else if (contract.equals("appliances")){
//                            model.addAttribute("sort_product", productRepository.findByCategoryOrderByPriceAsc(Float.parseFloat(ot), Float.parseFloat(dO), 3));
//                        } else if (contract.equals("clothes")){
//                            model.addAttribute("sort_product", productRepository.findByCategoryOrderByPriceAsc(Float.parseFloat(ot), Float.parseFloat(dO), 2));
//                        }
//                    } else {
//                        model.addAttribute("sort_product", productRepository.findAllOrderByPriceAsc(Float.parseFloat(ot), Float.parseFloat(dO)));
//                    }
//                } else if (price.equals("sorted_by_descending_price")){
//                    if (!contract.isEmpty()){
//                        if(contract.equals("furniture")){
//                            model.addAttribute("sort_product", productRepository.findByCategoryOrderByPriceDesc(Float.parseFloat(ot), Float.parseFloat(dO), 1));
//                        }else if (contract.equals("appliances")) {
//                            model.addAttribute("sort_product", productRepository.findByCategoryOrderByPriceDesc(Float.parseFloat(ot), Float.parseFloat(dO), 3));
//                        } else if (contract.equals("clothes")) {
//                            model.addAttribute("sort_product", productRepository.findByCategoryOrderByPriceDesc(Float.parseFloat(ot), Float.parseFloat(dO), 2));
//                        }
//                    } else {
//                        model.addAttribute("sort_product", productRepository.findAllOrderByPriceAsc(Float.parseFloat(ot), Float.parseFloat(dO)));
//                    }
//                }
//            } else {
//                model.addAttribute("sort_product", productRepository.findAllPriceGreaterThanEqualAndPriceLessThanEqual(Float.parseFloat(ot), Float.parseFloat(dO)));
//            }
//        }
//        model.addAttribute("value_price_ot", ot);
//        model.addAttribute("value_price_do", dO);
//        return "product/product";
//    }
}
