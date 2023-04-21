package com.example.finalfullstack.services;

import com.example.finalfullstack.models.Cart;
import com.example.finalfullstack.repositories.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartService {
    private  final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional
    public void upgradeCartById(Cart cart, int id, int quantity){
        cart.setId(id);
        cart.setQuantity(cart.getQuantity() + quantity);
        cartRepository.save(cart);
    }

    public List<Cart> getByPersonAndProduct(int person_id, int product_id){
        return cartRepository.findByPersonIdAndProductId(person_id, product_id);
    }

    public List<Cart> getByPerson(int person_id){
        return cartRepository.findByPersonId(person_id);
    }

    @Transactional
    public void saveCart(Cart cart){
        cartRepository.save(cart);
    }

    @Transactional
    public void deleteCartByPersonAndProduct(int person_id, int product_id){
        cartRepository.deleteCartByProductIdAndByPersonId(product_id, person_id);
    }
}
