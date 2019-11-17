package com.shopping.vn.dto;

import com.shopping.vn.entity.SizeColor;
import lombok.Data;

@Data
public class SizeColorDto {
  private Long id;
  private String nameColor;
  private Integer number;
  private ColorDto colorDto;
  private SizeDto sizeDto;
  public static final SizeColorDto convert(SizeColor productColor) {
    SizeColorDto productColorDto = new SizeColorDto();
    productColorDto.setId(productColor.getId());
    productColorDto.setNumber(productColor.getNumber());
    productColorDto.setNameColor(productColor.getColor().getName());
    return productColorDto;
  }
  
}
