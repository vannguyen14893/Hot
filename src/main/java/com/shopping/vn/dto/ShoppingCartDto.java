package com.shopping.vn.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartDto {
  private Long id;
  private BigDecimal grandTotal;
  private List<CartItemDto> cartItemList;
  private UserDto user;
}
