package com.example.ecom.controller.admin;

import com.example.ecom.dto.OrderDto;
import com.example.ecom.service.admin.adminOrder.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @GetMapping("/placedOrder")
    public ResponseEntity<List<OrderDto>>getAllPlacedOrders(){
       return ResponseEntity.status(HttpStatus.OK)
               .body(adminOrderService.getAllPlacedOrders());
    }


    @GetMapping("/order/{orderId}/{status}")
    public ResponseEntity<?>changeOrderStatus(@PathVariable("orderId")Long orderId ,
                                              @PathVariable("status")String status){
         var order = adminOrderService.changeOrderStatus(orderId,status);
         if(order==null){
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
         }
         else{
             return ResponseEntity.status(HttpStatus.OK).body(order);
         }
    }
}
