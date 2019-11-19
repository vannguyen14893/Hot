package com.shopping.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopping.vn.entity.BookToCartItem;
import com.shopping.vn.entity.CartItem;

public interface BookToCartItemRepository extends JpaRepository<BookToCartItem, Long> {
  void deleteByCartItem(CartItem cartItem);
}
