package com.shopping.vn.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
@Data
public class CartItemDto {
  private Long id;
  private int qty;
  private BigDecimal subtotal;
  private ProductDto product;
  private List<BookToCartItemDto> bookToCartItemList;
  private ShoppingCartDto shoppingCart;
  private OrderDto order;
}
