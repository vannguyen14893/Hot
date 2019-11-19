package com.shopping.vn.service;

import com.shopping.vn.dto.CartItemDto;
import com.shopping.vn.dto.ProductDto;
import com.shopping.vn.entity.CartItem;

public interface CartItemService {
  CartItem addBookToCartItem(ProductDto productDto, int qty);

  Boolean removeCartItem(CartItemDto cartItem);

  CartItem save(CartItemDto cartItem);

  CartItemDto findById(Long id);

  CartItem updateCartItem(CartItemDto cartItem);
}
