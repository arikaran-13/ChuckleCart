package com.example.ecom.repository;

import com.example.ecom.entity.customer.Order;
import com.example.ecom.enums.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
    List<Order> findAllByOrderStatusIn(List<OrderStatus>orderStatuses);
    List<Order> findByUserIdAndOrderStatusIn(Long userId, List<OrderStatus> orderStatus);

}
