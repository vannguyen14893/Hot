package com.shopping.vn.dto;

import com.shopping.vn.entity.ProductColor;
import lombok.Data;

@Data
public class ProductColorDto {
  private Long id;
  private String nameColor;
  private Integer number;

  public static final ProductColorDto convert(ProductColor productColor) {
    ProductColorDto productColorDto = new ProductColorDto();
    productColorDto.setId(productColor.getId());
    productColorDto.setNumber(productColor.getNumber());
    productColorDto.setNameColor(productColor.getColor().getName());
    return productColorDto;
  }
}
