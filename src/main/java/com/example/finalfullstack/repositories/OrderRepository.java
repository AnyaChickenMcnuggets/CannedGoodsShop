package com.example.finalfullstack.repositories;

import com.example.finalfullstack.models.Order;
import com.example.finalfullstack.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {


    List<Order> findByPersonOrderByDateTimeDesc(Person person);

}
