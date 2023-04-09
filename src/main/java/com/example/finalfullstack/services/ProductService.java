package com.example.finalfullstack.services;

import com.example.finalfullstack.models.Category;
import com.example.finalfullstack.models.Product;
import com.example.finalfullstack.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Product getProductById(int id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    @Transactional
    public void saveProduct(Product product, Category category){
        product.setCategory(category);
        productRepository.save(product);
    }

    @Transactional
    public void updateProductById(Product product, int id){
        product.setId(id);
        productRepository.save(product);
    }

    @Transactional
    public void deleteProductById(int id){
        productRepository.deleteById(id);
    }
    // TODO добавить методы репозитория!

    public List<Product> getProductByTitleContaining(String title){
        return productRepository.findByTitleContainingIgnoreCase(title);
    }
}
