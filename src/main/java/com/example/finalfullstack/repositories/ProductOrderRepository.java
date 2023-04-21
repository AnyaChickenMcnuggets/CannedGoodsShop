package com.example.finalfullstack.repositories;

import com.example.finalfullstack.models.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {

    List<ProductOrder> findProductOrdersByProductIdEquals(int productId);

}
