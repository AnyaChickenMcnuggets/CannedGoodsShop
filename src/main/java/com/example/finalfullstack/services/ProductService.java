package com.example.finalfullstack.services;

import com.example.finalfullstack.models.Category;
import com.example.finalfullstack.models.Product;
import com.example.finalfullstack.models.ProductOrder;
import com.example.finalfullstack.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOrderService productOrderService;

    public ProductService(ProductRepository productRepository, ProductOrderService productOrderService) {
        this.productRepository = productRepository;
        this.productOrderService = productOrderService;
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Product getProductById(int id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(productRepository.findById(1).orElseThrow());
    }

    public List<Product> getByTitle(String search){
        return productRepository.findByTitleContainingIgnoreCaseOrderByPrice(search.toLowerCase());
    }

    public List<Product> getByPriceAsc(String search, String ot, String dO){
        return productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(search.toLowerCase(), Integer.parseInt(ot), Integer.parseInt(dO));
    }

    public List<Product> getByPriceDesc(String search, String ot, String dO){
        return productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualDesc(search.toLowerCase(), Integer.parseInt(ot), Integer.parseInt(dO));
    }
    public List<Product> getByCategoryAndPriceAsc(String search, String ot, String dO, int c){
        return productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualAndCategory(search.toLowerCase(), Integer.parseInt(ot), Integer.parseInt(dO), c);
    }
    public List<Product> getByCategoryAndPriceDesc(String search, String ot, String dO, int c){
        return productRepository.findByTitleContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualAndCategoryDesc(search.toLowerCase(), Integer.parseInt(ot), Integer.parseInt(dO), c);
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
        List<ProductOrder> productOrderList = productOrderService.getAllByProduct(id);
        for (ProductOrder productOrder : productOrderList){
            productOrder.setProductId(1);
            productOrderService.saveProductOrder(productOrder);
        }
        productRepository.deleteById(id);
    }


}
