package com.shopping.vn.dto;

import lombok.Data;

@Data
public class BookToCartItemDto {
  private Long id;
  private ProductDto productDto;
  private CartItemDto cartItemDto;
}
