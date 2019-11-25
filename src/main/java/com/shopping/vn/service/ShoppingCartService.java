package com.shopping.vn.service;

import com.shopping.vn.entity.ShoppingCart;

public interface ShoppingCartService {
  ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

  void clearShoppingCart(ShoppingCart shoppingCart);
}
