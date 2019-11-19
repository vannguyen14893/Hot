package com.shopping.vn.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shopping.vn.entity.CartItem;
import com.shopping.vn.entity.ShoppingCart;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
  List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
  
}
