package com.shopping.vn.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopping.vn.entity.CartItem;
import com.shopping.vn.entity.ShoppingCart;
import com.shopping.vn.repository.CartItemRepository;
import com.shopping.vn.repository.ShoppingCartRepository;
import com.shopping.vn.service.CartItemService;
import com.shopping.vn.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
  @Autowired
  private CartItemService cartItemService;

  @Autowired
  private ShoppingCartRepository shoppingCartRepository;
  @Autowired
  private CartItemRepository cartItemRepository;

  @Override
  public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
    BigDecimal cartTotal = new BigDecimal(0);

    List<CartItem> cartItemList = cartItemRepository.findByShoppingCart(shoppingCart);

    for (CartItem cartItem : cartItemList) {
      if (cartItem.getProduct().getStockNumber() > 0) {
       // cartItemService.updateCartItem(cartItem);
        cartTotal = cartTotal.add(cartItem.getSubtotal());
      }
    }

    shoppingCart.setGrandTotal(cartTotal);

    shoppingCartRepository.save(shoppingCart);

    return shoppingCart;
  }

  @Override
  public void clearShoppingCart(ShoppingCart shoppingCart) {
    List<CartItem> cartItemList = cartItemRepository.findByShoppingCart(shoppingCart);
    
    for(CartItem cartItem : cartItemList) {
        cartItem.setShoppingCart(null);
        cartItemRepository.save(cartItem);
    }
    
    shoppingCart.setGrandTotal(new BigDecimal(0));
    
    shoppingCartRepository.save(shoppingCart);
    
  }

}
