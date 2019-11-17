package com.shopping.vn.dto;

import java.util.ArrayList;
import java.util.List;
import com.shopping.vn.entity.Size;
import com.shopping.vn.entity.SizeColor;
import lombok.Data;

@Data
public class SizeDto {
  private Long id;
  private Integer number;
  private List<SizeColorDto> sizeColorDtos;

  public static final SizeDto convert(Size size) {
    SizeDto sizeDto = new SizeDto();
    sizeDto.setId(size.getId());
    sizeDto.setNumber(size.getNumber());
    List<SizeColorDto> sizeColorDtos=new ArrayList<>();
    for (SizeColor productColor : size.getSizeColors()) {
      sizeColorDtos.add(SizeColorDto.convert(productColor));
      
    }
    sizeDto.setSizeColorDtos(sizeColorDtos);
    return sizeDto;
  }
}
