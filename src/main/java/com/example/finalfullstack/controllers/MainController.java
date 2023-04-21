package com.example.finalfullstack.controllers;

import com.example.finalfullstack.enums.Status;
import com.example.finalfullstack.models.*;
import com.example.finalfullstack.repositories.*;
import com.example.finalfullstack.security.PersonDetails;
import com.example.finalfullstack.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

    private final ProductService productService;
    private final CartService cartService;
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final ProductOrderService productOrderService;

    public MainController(ProductService productService, CategoryService categoryService, CartService cartService, OrderService orderService, ProductOrderService productOrderService) {

        this.productService = productService;
        this.cartService = cartService;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.productOrderService = productOrderService;
    }

    @GetMapping("/my/product")
    public String index(@RequestParam(name = "search", required = false, defaultValue = "") String search, @RequestParam(name = "ot", required = false, defaultValue = "") String ot, @RequestParam(name = "do", required = false, defaultValue = "") String dO, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "contract", required = false, defaultValue = "") String contract, Model model){
        // объект аутентификации -> обращаемся к контексту и на нем вызываем метод аутентификации. Из сессии текущего пользователя получаем объект, который был положен в данную сессию после аутентификации пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        String role = personDetails.getPerson().getRole();
        if (role.equals("ROLE_ADMIN")){
            return "redirect:/admin";
        }

        model.addAttribute("sort_product", productService.getByTitle(search));
        model.addAttribute("value_price", price);
        model.addAttribute("value_contract", contract);
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", dO);
        model.addAttribute("value_search", search);
        return "user/index";
    }

    @PostMapping("/my/product")
    public String productSpecific(@RequestParam(name = "search", required = false, defaultValue = "") String search, @RequestParam(name = "ot", required = false, defaultValue = "") String ot, @RequestParam(name = "do", required = false, defaultValue = "") String dO, @RequestParam(name = "price", defaultValue = "sorted_by_ascending_price") String price, @RequestParam(value = "contract", required = false, defaultValue = "null_category") String contract, Model model){
        // объект аутентификации -> обращаемся к контексту и на нем вызываем метод аутентификации. Из сессии текущего пользователя получаем объект, который был положен в данную сессию после аутентификации пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        String role = personDetails.getPerson().getRole();
        if (role.equals("ROLE_ADMIN")){
            return "redirect:/admin";
        }

        if (ot.equals("")) ot = "0";
        if (Float.parseFloat(ot) < 0) ot = "0";
        if (dO.equals("")) dO = "100000";
        if (Float.parseFloat(dO) < 0 || Float.parseFloat(dO) > 100000) dO = "100000";
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

        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("value_price", price);
        model.addAttribute("value_contract", contract);
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", dO);
        model.addAttribute("value_search", search);
        return "user/index";
    }

    @GetMapping("/my/product/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "user/info_product";
    }

    @PostMapping("/my/cart/add/{id}")
    public String addProductInCart(@PathVariable("id") int id, @RequestParam("quantity") int quantity){
        Product product = productService.getProductById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        int person_id = personDetails.getPerson().getId();

        List<Cart> cartList = cartService.getByPersonAndProduct(person_id, product.getId());

        if (cartList.isEmpty()){
            Cart cart = new Cart(person_id, product.getId(), quantity);
            cartService.saveCart(cart);
        } else {
            cartService.upgradeCartById(cartList.get(0), cartList.get(0).getId(), quantity);
        }

        return "redirect:/my/cart";
    }

    @GetMapping("/my/cart")
    public String cart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        int person_id = personDetails.getPerson().getId();

        List<Cart> cartList = cartService.getByPerson(person_id);
        List<Product> productList = new ArrayList<>();
        for (Cart cart:cartList) {
            productList.add(productService.getProductById(cart.getProductId()));
        }

        float finalPrice = 0;
        for (Product product :
                productList) {
            finalPrice += product.getPrice() * cartService.getByPersonAndProduct(person_id, product.getId()).get(0).getQuantity();
        }

        model.addAttribute("cart", cartList);
        model.addAttribute("final_price", finalPrice);
        model.addAttribute("cart_product", productList);
        return "user/cart";
    }

    @GetMapping("/my/cart/delete/{id}")
    public String deleteProductFromCart(@PathVariable("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        int person_id = personDetails.getPerson().getId();

        cartService.deleteCartByPersonAndProduct(person_id, id);

        return "redirect:/my/cart";
    }

    @GetMapping("/my/cart/add_one/{id}")
    public String addOneProductFromCart(@PathVariable("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        int person_id = personDetails.getPerson().getId();

        Cart cart = cartService.getByPersonAndProduct(person_id, id).get(0);
        cartService.upgradeCartById(cart, cart.getId(), 1);

        return "redirect:/my/cart";
    }

    @GetMapping("/my/cart/remove_one/{id}")
    public String removeOneProductFromCart(@PathVariable("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        int person_id = personDetails.getPerson().getId();

        Cart cart = cartService.getByPersonAndProduct(person_id, id).get(0);
        if (cart.getQuantity()==1){
            cartService.deleteCartByPersonAndProduct(person_id, id);
        } else {
            cartService.upgradeCartById(cart, cart.getId(), -1);
        }

        return "redirect:/my/cart";
    }

    @GetMapping("/my/cart/cancel")
    public String cancelCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        int person_id = personDetails.getPerson().getId();

        List<Cart> cartList = cartService.getByPerson(person_id);
        for (Cart cart:cartList) {
            cartService.deleteCartByPersonAndProduct(person_id, cart.getProductId());
        }

        return "redirect:/my/cart";
    }

    @GetMapping("/my/order/create")
    public String order(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        int person_id = personDetails.getPerson().getId();

        List<Cart> cartList = cartService.getByPerson(person_id);
        List<Product> productList = new ArrayList<>();
        for (Cart cart:cartList) {
            productList.add(productService.getProductById(cart.getProductId()));
        }

        float finalPrice = 0;
        for (Product product :
                productList) {
            finalPrice += product.getPrice() * cartService.getByPersonAndProduct(person_id, product.getId()).get(0).getQuantity();
        }

        String uuid = UUID.randomUUID().toString() + (int) finalPrice + person_id;
        Order newOrder = new Order(uuid, personDetails.getPerson(), finalPrice, Status.Оформлен);
        orderService.saveOrder(newOrder);

        for (Product product :
                productList) {
            ProductOrder productOrder = new ProductOrder(cartService.getByPersonAndProduct(person_id, product.getId()).get(0).getQuantity(), product.getPrice(), newOrder.getId(), product.getId(), product.getTitle());
            productOrderService.saveProductOrder(productOrder);
            cartService.deleteCartByPersonAndProduct(person_id, product.getId());
        }

        return "redirect:/my/order";
    }

    @GetMapping("/my/order")
    public String orderUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<Order> orderList = orderService.getOrderByPerson(personDetails.getPerson());

        List<ProductOrder> productOrderList = productOrderService.getAll();
        List<Product> productList = productService.getAllProduct();

        model.addAttribute("products", productList);
        model.addAttribute("productOrders", productOrderList);
        model.addAttribute("orders", orderList);
        return "user/order";
    }
}

