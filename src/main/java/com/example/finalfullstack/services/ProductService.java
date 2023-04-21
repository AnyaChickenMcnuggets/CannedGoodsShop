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

    public List<Product> getByTitle(String search){
        return productRepository.findByTitleContainingIgnoreCaseOrderByPrice(search.toLowerCase());
    }

    public List<Product> getByPriceAsc(String search, String ot, String dO){
        return productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(dO));
    }

    public List<Product> getByPriceDesc(String search, String ot, String dO){
        return productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(dO));
    }
    public List<Product> getByCategoryAndPriceAsc(String search, String ot, String dO, int c){
        return productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualAndCategory(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(dO), c);
    }
    public List<Product> getByCategoryAndPriceDesc(String search, String ot, String dO, int c){
        return productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualAndCategoryDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(dO), c);
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


}
