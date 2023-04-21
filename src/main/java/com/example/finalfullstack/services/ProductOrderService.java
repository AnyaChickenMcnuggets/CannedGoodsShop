package com.example.finalfullstack.services;

import com.example.finalfullstack.models.ProductOrder;
import com.example.finalfullstack.repositories.ProductOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;

    public ProductOrderService(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    @Transactional
    public void saveProductOrder(ProductOrder productOrder){
        productOrderRepository.save(productOrder);
    }

    public List<ProductOrder> getAll(){
        return productOrderRepository.findAll();
    }
}
