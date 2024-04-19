package com.example.ecom.service.customer.cart;

import com.example.ecom.dto.*;
import com.example.ecom.entity.admin.Coupon;
import com.example.ecom.entity.admin.Product;
import com.example.ecom.entity.customer.CartItem;
import com.example.ecom.entity.customer.Order;
import com.example.ecom.entity.customer.User;
import com.example.ecom.enums.OrderStatus;
import com.example.ecom.exception.ValidationException;
import com.example.ecom.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CartServiceImpl implements CartService{
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final AdminProductRepository productRepository;
    private final CouponRepository couponRepository;


    public ResponseEntity<?> addProductToCart(AddProductToCartDto addProductToCartDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductToCartDto.getUserId(), OrderStatus.Pending);
        Optional<User> optUser = userRepository.findById(addProductToCartDto.getUserId());
        if(activeOrder==null){
            if(optUser.isPresent()){
                createNewCartForUser(optUser.get());
                activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductToCartDto.getUserId(),OrderStatus.Pending);
            }
            else{
                throw new ValidationException("User not found");
            }
        }
        Optional<CartItem> optCartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(addProductToCartDto.getProductId(),activeOrder.getId(),addProductToCartDto.getUserId());
        if(optCartItem.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            Optional<Product> optProduct = productRepository.findById(addProductToCartDto.getProductId());

            if(optProduct.isPresent() && optUser.isPresent()){
                CartItem cartItem = new CartItem();
                cartItem.setUser(optUser.get());
                cartItem.setProduct(optProduct.get());
                cartItem.setQuantity(1L);
                cartItem.setPrice(optProduct.get().getPrice());
                cartItem.setOrder(activeOrder);

                CartItem updatedCart = cartItemRepository.save(cartItem);
                var amt = activeOrder.getTotalAmount() + cartItem.getPrice();
                activeOrder.setTotalAmount(amt);
                activeOrder.setAmount(amt);

                activeOrder.getCartItems().add(cartItem);
                orderRepository.save(activeOrder); // Saving the updated order

                return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
            }
        }
    }

    private void createNewCartForUser(User user) {
        var order = new Order();
        order.setAmount(0L);
        order.setDiscount(0L);
        order.setOrderStatus(OrderStatus.Pending);
        order.setTotalAmount(0L);
        order.setUser(user);
        orderRepository.save(order);
    }

    @Override
    public OrderDto getCartByUserId(Long id) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(id , OrderStatus.Pending);
        if(activeOrder==null){
            return null;
        }
       List<CartItemDto> cartItemDto= activeOrder.getCartItems().stream().map(CartItem::getCartDto).collect(Collectors.toList());
        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemDto);
        if(activeOrder.getCoupon() != null){
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }
        return orderDto;
    }

    public OrderDto applyCoupon(Long userId,String couponCode){
        var activeOrder = orderRepository.findByUserIdAndOrderStatus(userId,OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(couponCode).orElseThrow(()-> new ValidationException("Coupon Not found"));

        if(isCouponExpired(coupon)){
            throw new ValidationException("Coupon Expired : "+ couponCode);
        }
        var discountedAmt = ( coupon.getDiscount() / 100.0 ) * activeOrder.getTotalAmount();
        var netAmount = activeOrder.getTotalAmount() - discountedAmt;
        //log.info("discounter adn net amount after applying coupon {} , {} : ",discountedAmt,netAmount);
        //activeOrder.setTotalAmount((long)netAmount);
        activeOrder.setAmount((long)netAmount);
        activeOrder.setDiscount((long)discountedAmt);
        activeOrder.setCoupon(coupon);

        Order updatedOrder = orderRepository.save(activeOrder);
    //   log.info("updated order {}",updatedOrder);
        return updatedOrder.getOrderDto();
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public OrderDto updateCartItems(UpdateCartItemsForActiveOrder updateOrderRequest) {
        var  userId = updateOrderRequest.getUserId();
        var quantity = updateOrderRequest.getQuantity();

        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId,OrderStatus.Pending);
        if(activeOrder==null){
            throw new ValidationException("Cart is empty");
        }
        activeOrder
                .getCartItems()
                .stream()
                .filter(cartItem ->
                        cartItem.getProduct().getId()
                                .equals(updateOrderRequest.getProductId())
                ).forEach(cartItem ->{
                    cartItem.setQuantity(quantity);
                } );

       Optional<Long> singleProductPrice = activeOrder.getCartItems()
               .stream().filter(cartItem -> cartItem.getProduct().getId().equals(updateOrderRequest.getProductId())).map(cartItem -> cartItem.getProduct().getPrice()).findFirst();
             if(singleProductPrice.isPresent()){
                 var totalAmount = singleProductPrice.get() * quantity;
                 activeOrder.setTotalAmount(totalAmount);
                 Order updatedOrder = orderRepository.save(activeOrder);
                 return updatedOrder.getOrderDto();
             }
             else{
                 throw new ValidationException("Product price cannot be empty");
             }


    }
    private boolean isCouponExpired(Coupon coupon) {
        var currentDate = new Date();
        return coupon.getExpirationDate()!= null &&
                currentDate.after(coupon.getExpirationDate());
    }

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(),OrderStatus.Pending);
        Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());

        if(activeOrder!=null && optionalUser.isPresent()){
          activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
          activeOrder.setAddress(placeOrderDto.getAddress());
          activeOrder.setDate(new Date());
          activeOrder.setOrderStatus(OrderStatus.Placed);
          activeOrder.setOrderTrackId(UUID.randomUUID());
          orderRepository.save(activeOrder);

            var order = new Order();
            order.setAmount(0L);
            order.setDiscount(0L);
            order.setOrderStatus(OrderStatus.Pending);
            order.setTotalAmount(0L);
            order.setUser(optionalUser.get());
            orderRepository.save(order);

          return activeOrder.getOrderDto();
        }else{
            return null;
        }
    }

    public List<OrderDto>getMyPlacedOrders(Long userId){
     return orderRepository
             .findByUserIdAndOrderStatusIn(userId,List.of(
                     OrderStatus.Placed,
                     OrderStatus.Shipped,
                     OrderStatus.Delivered))
             .stream()
             .map(Order::getOrderDto)
             .collect(Collectors.toList());
    }

}
