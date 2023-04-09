package com.example.finalfullstack.repositories;

import com.example.finalfullstack.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByTitleContainingIgnoreCase(String name);

    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) like '%?1')) and (price >= ?2 and price <= ?3)", nativeQuery = true)
    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(String title, float ot, float dO);

    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) like '%?1')) and (price >= ?2 and price <= ?3) order by price", nativeQuery = true)
    List<Product> findByTitleOrderByPriceAsc(String title, float ot, float dO);

    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) like '%?1')) and (price >= ?2 and price <= ?3) order by price desc", nativeQuery = true)
    List<Product> findByTitleOrderByPriceDesc(String title, float ot, float dO);

    @Query(value = "select * from product where category_id = ?4 and ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) like '%?1')) and (price >= ?2 and price <= ?3) order by price", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceAsc(String title, float ot, float dO, int category);

    @Query(value = "select * from product where category_id = ?4 and ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) like '%?1')) and (price >= ?2 and price <= ?3) order by price desc", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceDesc(String title, float ot, float dO, int category);
    @Query(value = "select * from product where price >= ?1 and price <= ?2", nativeQuery = true)
    List<Product> findAllPriceGreaterThanEqualAndPriceLessThanEqual(float ot, float dO);
    @Query(value = "select * from product where category_id = ?3 and (price >= ?1 and price <= ?2) order by price", nativeQuery = true)
    List<Product> findByCategoryOrderByPriceAsc(float ot, float dO, int category);

    @Query(value = "select * from product where price >= ?1 and price <= ?2 order by price", nativeQuery = true)
    List<Product> findAllOrderByPriceAsc(float ot, float dO);

    @Query(value = "select * from product where category_id = ?3 and (price >= ?1 and price <= ?2) order by price desc", nativeQuery = true)
    List<Product> findByCategoryOrderByPriceDesc(float ot, float dO, int category);

    @Query(value = "select * from product where price >= ?1 and price <= ?2 order by price desc", nativeQuery = true)
    List<Product> findAllOrderByPriceDesc(float ot, float dO);
}
