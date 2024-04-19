package com.example.ecom.service.admin.adminOrder;

import com.example.ecom.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {
     List<OrderDto> getAllPlacedOrders();
     OrderDto changeOrderStatus(Long orderId, String status);
}
