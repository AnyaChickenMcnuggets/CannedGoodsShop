package com.example.finalfullstack.services;

import com.example.finalfullstack.enums.Status;
import com.example.finalfullstack.models.Order;
import com.example.finalfullstack.models.Product;
import com.example.finalfullstack.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(int id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    @Transactional
    public void updateProductById(Order order, int id){
        order.setId(id);
        String temp = String.valueOf(order.getStatus());
        if (temp.equals("Оформлен")) order.setStatus(Status.valueOf(Status.Оплачен.getDisplayValue()));
        if (temp.equals("Оплачен")) order.setStatus(Status.valueOf(Status.Доставка.getDisplayValue()));
        if (temp.equals("Доставка")) order.setStatus(Status.valueOf(Status.Получен.getDisplayValue()));
        if (temp.equals("Отменен")) order.setStatus(Status.valueOf(Status.Оформлен.getDisplayValue()));
        orderRepository.save(order);
    }

    @Transactional
    public void stopProductById(Order order, int id){
        order.setId(id);
        order.setStatus(Status.valueOf(Status.Отменен.getDisplayValue()));
        orderRepository.save(order);
    }
}
