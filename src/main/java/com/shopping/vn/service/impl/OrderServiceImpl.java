package com.shopping.vn.service.impl;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shopping.vn.dto.OrderDto;
import com.shopping.vn.entity.CartItem;
import com.shopping.vn.entity.Order;
import com.shopping.vn.entity.Product;
import com.shopping.vn.entity.ShoppingCart;
import com.shopping.vn.entity.User;
import com.shopping.vn.repository.CartItemRepository;
import com.shopping.vn.repository.OrderRepository;
import com.shopping.vn.repository.ShoppingCartRepository;
import com.shopping.vn.repository.UserRepository;
import com.shopping.vn.service.NotificationService;
import com.shopping.vn.service.OrderService;
import com.shopping.vn.service.ShoppingCartService;
import com.shopping.vn.utils.Utils;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private CartItemRepository cartItemRepository;
  @Autowired
  private ShoppingCartRepository shoppingCartRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ShoppingCartService shoppingCartService;
 
  @Override
  public synchronized Order createOrder(OrderDto orderDto) {
    User user = userRepository.findUserByEmail(Utils.getPrincipal());
    ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(user.getEmail());
    
    Order order = new Order();
    order.setOrderStatus("created");
    order.setShippingMethod(orderDto.getShippingMethod());
    order.setOrderTotal(shoppingCart.getGrandTotal());
    order.setOrderDate(Calendar.getInstance().getTime());
    order.setShippingDate(orderDto.getShippingDate());
    order.setUser(user);
    
    shoppingCartService.clearShoppingCart(shoppingCart);
    
    List<CartItem> cartItems = cartItemRepository.findByShoppingCart(shoppingCart);
    for (CartItem cartItem : cartItems) {
      Product product = cartItem.getProduct();
      cartItem.setOrder(order);
      product.setNumber(product.getNumber() - cartItem.getQty());
    }
    order.setCartItemList(cartItems);
    orderRepository.save(order);
    
    return order;
  }

}
