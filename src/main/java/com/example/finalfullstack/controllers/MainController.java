package com.example.finalfullstack.controllers;

import com.example.finalfullstack.enums.Status;
import com.example.finalfullstack.models.*;
import com.example.finalfullstack.repositories.CartRepository;
import com.example.finalfullstack.repositories.OrderRepository;
import com.example.finalfullstack.repositories.ProductOrderRepository;
import com.example.finalfullstack.repositories.ProductRepository;
import com.example.finalfullstack.security.PersonDetails;
import com.example.finalfullstack.services.PersonService;
import com.example.finalfullstack.services.ProductService;
import com.example.finalfullstack.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

    private final PersonValidator personValidator;
    private final PersonService personService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductOrderRepository productOrderRepository;

    public MainController(PersonValidator personValidator, PersonService personService, ProductService productService, ProductRepository productRepository, CartRepository cartRepository, OrderRepository orderRepository, ProductOrderRepository productOrderRepository) {
        this.personValidator = personValidator;
        this.personService = personService;
        this.productService = productService;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productOrderRepository = productOrderRepository;
    }

    @GetMapping("/my/product")
    public String index(@RequestParam(name = "search", defaultValue = "") String search, Model model){
        // объект аутентификации -> обращаемся к контексту и на нем вызываем метод аутентификации. Из сессии текущего пользователя получаем объект, который был положен в данную сессию после аутентификации пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        String role = personDetails.getPerson().getRole();
        if (role.equals("ROLE_ADMIN")){
            return "redirect:/admin";
        }
        model.addAttribute("sort_product", productRepository.findByTitleContainingIgnoreCase(search));
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("value_search", search);
        return "user/index";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("person") Person person){
        return "registration";
    }

    @PostMapping("/registration")
    public String resultRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "registration";
        }
        personService.register(person);
        return "redirect:/my";
    }
    @GetMapping("/my/product/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "user/info_product";
    }

    @PostMapping("/my/product/search")
    public String productSearch(@RequestParam("search") String search, Model model){

        model.addAttribute("sort_product", productRepository.findByTitleContainingIgnoreCase(search));
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("value_search", search);
        return "user/index";
    }


    @PostMapping("/my/product/sort")
    public String productSort(@RequestParam("ot") String ot, @RequestParam("do") String dO, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "contract", required = false, defaultValue = "") String contract, Model model){
        model.addAttribute("products", productService.getAllProduct());

        if (!ot.isEmpty() & !dO.isEmpty()){
            if (!price.isEmpty()){
                if (price.equals("sorted_by_ascending_price")){
                    if (!contract.isEmpty()){
                        if (contract.equals("furniture")){
                            model.addAttribute("sort_product", productRepository.findByCategoryOrderByPriceAsc(Float.parseFloat(ot), Float.parseFloat(dO), 1));
                        } else if (contract.equals("appliances")){
                            model.addAttribute("sort_product", productRepository.findByCategoryOrderByPriceAsc(Float.parseFloat(ot), Float.parseFloat(dO), 3));
                        } else if (contract.equals("clothes")){
                            model.addAttribute("sort_product", productRepository.findByCategoryOrderByPriceAsc(Float.parseFloat(ot), Float.parseFloat(dO), 2));
                        }
                    } else {
                        model.addAttribute("sort_product", productRepository.findAllOrderByPriceAsc(Float.parseFloat(ot), Float.parseFloat(dO)));
                    }
                } else if (price.equals("sorted_by_descending_price")){
                    if (!contract.isEmpty()){
                        if(contract.equals("furniture")){
                            model.addAttribute("sort_product", productRepository.findByCategoryOrderByPriceDesc(Float.parseFloat(ot), Float.parseFloat(dO), 1));
                        }else if (contract.equals("appliances")) {
                            model.addAttribute("sort_product", productRepository.findByCategoryOrderByPriceDesc(Float.parseFloat(ot), Float.parseFloat(dO), 3));
                        } else if (contract.equals("clothes")) {
                            model.addAttribute("sort_product", productRepository.findByCategoryOrderByPriceDesc(Float.parseFloat(ot), Float.parseFloat(dO), 2));
                        }
                    } else {
                        model.addAttribute("sort_product", productRepository.findAllOrderByPriceAsc(Float.parseFloat(ot), Float.parseFloat(dO)));
                    }
                }
            } else {
                model.addAttribute("sort_product", productRepository.findAllPriceGreaterThanEqualAndPriceLessThanEqual(Float.parseFloat(ot), Float.parseFloat(dO)));
            }
        }
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", dO);
        return "user/index";
    }

    @GetMapping("/my/cart/add/{id}")
    public String addProductInCart(@PathVariable("id") int id, Model model){
        Product product = productService.getProductById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        int person_id = personDetails.getPerson().getId();

        Cart cart = new Cart(person_id, product.getId());
        cartRepository.save(cart);

        return "redirect:/my/cart";
    }

    @GetMapping("/my/cart")
    public String cart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        int person_id = personDetails.getPerson().getId();

        List<Cart> cartList = cartRepository.findByPersonId(person_id);
        List<Product> productList = new ArrayList<>();
        for (Cart cart:cartList) {
            productList.add(productService.getProductById(cart.getProductId()));
        }

        float finalPrice = 0;
        for (Product product :
                productList) {
            finalPrice += product.getPrice();
        }

        model.addAttribute("final_price", finalPrice);
        model.addAttribute("cart_product", productList);
        return "user/cart";
    }

    @GetMapping("/my/cart/delete/{id}")
    public String deleteProductFromCart(Model model, @PathVariable("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        int person_id = personDetails.getPerson().getId();

        List<Cart> cartList = cartRepository.findByPersonId(person_id);
        List<Product> productList = new ArrayList<>();
        for (Cart cart:cartList) {
            productList.add(productService.getProductById(cart.getProductId()));
        }
        cartRepository.deleteCartByProductIdAndByPersonId(id, person_id);

        return "redirect:/my/cart";
    }

    @GetMapping("/my/order/create")
    public String order(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        int person_id = personDetails.getPerson().getId();

        List<Cart> cartList = cartRepository.findByPersonId(person_id);
        List<Product> productList = new ArrayList<>();
        for (Cart cart:cartList) {
            productList.add(productService.getProductById(cart.getProductId()));
        }

        float finalPrice = 0;
        for (Product product :
                productList) {
            finalPrice += product.getPrice();
        }

        String uuid = UUID.randomUUID().toString() + (int) finalPrice + person_id;
        Order newOrder = new Order(uuid, personDetails.getPerson(), finalPrice, Status.Оформлен);
        orderRepository.save(newOrder);

        for (Product product :
                productList) {
            ProductOrder productOrder = new ProductOrder(1, product.getPrice(), newOrder.getId(), product.getId());
            productOrderRepository.save(productOrder);
            cartRepository.deleteCartByProductIdAndByPersonId(product.getId(), person_id);
        }

        return "redirect:/my/order";
    }

    @GetMapping("/my/order")
    public String orderUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<Order> orderList = orderRepository.findByPersonOrderByDateTimeDesc(personDetails.getPerson());

        List<ProductOrder> productOrderList = productOrderRepository.findAll();
        List<Product> productList = productRepository.findAll();

        model.addAttribute("products", productList);
        model.addAttribute("productOrders", productOrderList);
        model.addAttribute("orders", orderList);
        return "user/order";

    }
}

