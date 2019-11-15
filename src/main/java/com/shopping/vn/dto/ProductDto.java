package com.shopping.vn.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import com.shopping.vn.entity.Product;
import com.shopping.vn.entity.ProductColor;
import com.shopping.vn.entity.ProductSize;
import lombok.Data;


@Data
public class ProductDto {
  private Long id;
  private String name;
  private BigDecimal price;
  private BigDecimal priceSale;
  private String image;
  private String location;
  private String stock;
  private Integer number;
  private Integer status;
  private Integer vote;
  private CategoryDto category;
  private List<ProductSizeDto> productSizeDtos;
  private List<ProductColorDto> productColorDtos;
  private String nameCategory;
  private List<String> nameColors;
  private List<Integer> numberSizes;
  public static final ProductDto convert(Product product) {
   
    ProductDto productDto=new ProductDto();
    productDto.setId(product.getId());
    productDto.setName(product.getName());
    productDto.setPrice(product.getPrice());
    productDto.setPriceSale(product.getPriceSale());
    productDto.setImage(product.getImage());
    productDto.setLocation(product.getLocation());
    productDto.setStock(product.getStock());
    productDto.setNumber(product.getNumber());
    productDto.setStatus(product.getStatus());
    productDto.setVote(product.getVote());
    productDto.setNameCategory(product.getCategory().getName());
    List<ProductSize> productSizes=product.getProductSizes();
    for (ProductSize productSize : productSizes) {
      productDto.setProductSizeDtos(Arrays.asList(ProductSizeDto.convert(productSize)));
    }
    List<ProductColor> productColors=product.getProductColors();
    for (ProductColor productColor : productColors) {
      productDto.setProductColorDtos(Arrays.asList(ProductColorDto.convert(productColor)));
    }
    return productDto;
  }
}
