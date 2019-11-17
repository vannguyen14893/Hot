package com.shopping.vn.dto;

import java.util.List;
import com.shopping.vn.entity.ProductSize;
import lombok.Data;

@Data
public class ProductSizeDto {
  private Long id;
  private Integer number;
  private Integer size;
  private SizeDto sizeDto;
  private List<SizeColorDto> sizeColorDtos;
  public static final ProductSizeDto convert(ProductSize productSize) {
    ProductSizeDto productSizeDto = new ProductSizeDto();
    productSizeDto.setId(productSize.getId());
    productSizeDto.setNumber(productSize.getNumber());
    productSizeDto.setSize(productSize.getSize().getNumber());
    productSizeDto.setSizeDto(SizeDto.convert(productSize.getSize()));
    return productSizeDto;
  }
}
