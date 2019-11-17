package com.shopping.vn.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.shopping.vn.entity.Category;
import com.shopping.vn.entity.Product;
import com.shopping.vn.entity.SizeColor;
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
  private String description;
  private CategoryDto category;
  private List<ProductSizeDto> productSizeDtos;
  // private List<ProductColorDto> productColorDtos;
  private String nameCategory;
  private List<String> nameColors;
  private List<Integer> numberSizes;

  public static final ProductDto convert(Product product) {

    ProductDto productDto = new ProductDto();
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
    List<ProductSize> productSizes = product.getProductSizes();
    List<ProductSizeDto> productSizeDtos = new ArrayList<ProductSizeDto>();
    for (ProductSize productSize : productSizes) {
      productSizeDtos.add(ProductSizeDto.convert(productSize));
    }
    productDto.setProductSizeDtos(productSizeDtos);

    return productDto;
  }

  public static final Product convertCreate(ProductDto productDto, Category category,
      List<SizeColor> productColors, List<ProductSize> productSizes, Integer sum) {
    Product product = new Product();
    product.setDescription(productDto.getDescription());
    product.setName(productDto.getName());
    product.setLocation(productDto.getLocation());
    product.setPrice(productDto.getPrice());
    product.setStock(productDto.getStock());
    product.setStatus(productDto.getStatus());
    product.setNumber(sum);
    product.setCategory(category);
    // product.setProductColors(productColors);
    product.setProductSizes(productSizes);
    return product;
  }

  public static final Product convertUpdate(ProductDto productDto, Product product,
      Category category, List<SizeColor> productColors, List<ProductSize> productSizes,
      Integer sum) {
    product.setId(productDto.getId());
    product.setDescription(productDto.getDescription());
    product.setName(productDto.getName());
    product.setLocation(productDto.getLocation());
    product.setPrice(productDto.getPrice());
    product.setStock(productDto.getStock());
    product.setStatus(productDto.getStatus());
    product.setNumber(sum);
    product.setCategory(category);
    // product.setProductColors(productColors);
    product.setProductSizes(productSizes);
    return product;
  }
}
