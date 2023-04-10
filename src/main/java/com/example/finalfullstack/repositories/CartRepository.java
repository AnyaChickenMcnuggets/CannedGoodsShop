package com.example.finalfullstack.repositories;

import com.example.finalfullstack.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByPersonId(int id);

    List<Cart> findByPersonIdAndProductId(int person_id, int product_id);
    @Modifying
    @Query(value = "delete from product_cart where product_id = ?1 and person_id = ?2 ", nativeQuery = true)
    void deleteCartByProductIdAndByPersonId(int product_id, int person_id);
}
