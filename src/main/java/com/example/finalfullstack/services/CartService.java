package com.example.finalfullstack.services;

import com.example.finalfullstack.models.Cart;
import com.example.finalfullstack.repositories.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
