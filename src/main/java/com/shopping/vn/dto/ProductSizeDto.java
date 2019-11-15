package com.shopping.vn.dto;

import com.shopping.vn.entity.ProductSize;
import lombok.Data;

@Data
public class ProductSizeDto {
  private Long id;
  private Integer number;
  private Integer size;

  public static final ProductSizeDto convert(ProductSize productSize) {
    ProductSizeDto productSizeDto = new ProductSizeDto();
    productSizeDto.setId(productSize.getId());
    productSizeDto.setNumber(productSize.getNumber());
    productSizeDto.setSize(productSize.getSize().getNumber());
    return productSizeDto;
  }
}
