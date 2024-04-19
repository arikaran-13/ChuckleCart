package com.example.ecom.service.admin.adminOrder;

import com.example.ecom.dto.OrderDto;
import com.example.ecom.entity.customer.Order;
import com.example.ecom.enums.OrderStatus;
import com.example.ecom.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService{
    private final OrderRepository orderRepository;

    public List<OrderDto> getAllPlacedOrders(){
        List<Order> orderList = orderRepository.findAllByOrderStatusIn(
                List.of(OrderStatus.Placed,
                        OrderStatus.Delivered,
                        OrderStatus.Shipped)
        );
        return orderList
                .stream()
                .map(Order::getOrderDto)
                .collect(Collectors.toList());
    }

    public OrderDto changeOrderStatus(Long orderId, String status){
        Optional<Order> optOrder = orderRepository.findById(orderId);
        if(optOrder.isPresent()){
            Order order = optOrder.get();
            if(status.equals("Shipped")){
                order.setOrderStatus(OrderStatus.Shipped);
            }
            else if (status.equals("Delivered")){
                order.setOrderStatus(OrderStatus.Delivered);
            }
            return orderRepository.save(order).getOrderDto();
        }
        return null;
    }
}
