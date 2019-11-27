package com.shopping.vn.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.shopping.vn.entity.Category;
import com.shopping.vn.entity.Product;
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
	private String nameCategory;
	private List<String> nameColors;
	private List<Integer> numberSizes;
	private List<String> images;
    private Long count;
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
		productDto.setImages(product.getImages());
		productDto.setNameCategory(product.getCategory().getName());
		List<ProductSize> productSizes = product.getProductSizes();
		List<ProductSizeDto> productSizeDtos = new ArrayList<>();
		for (ProductSize productSize : productSizes) {
			productSizeDtos.add(ProductSizeDto.convert(productSize));
		}
		productDto.setProductSizeDtos(productSizeDtos);

		return productDto;
	}

	public static final Product convertCreate(ProductDto productDto) {
		Product product = new Product();
		product.setDescription(productDto.getDescription());
		product.setName(productDto.getName());
		product.setLocation(productDto.getLocation());
		product.setPrice(productDto.getPrice());
		product.setStock(productDto.getStock());
		product.setStatus(productDto.getStatus());
		product.setImages(productDto.getImages());
		product.setImage(productDto.getImage());
		return product;
	}

	public static final Product convertUpdate(ProductDto productDto, Product product, Category category,
			List<ProductSize> productSizes, Integer sum) {
		product.setId(productDto.getId());
		product.setDescription(productDto.getDescription());
		product.setName(productDto.getName());
		product.setLocation(productDto.getLocation());
		product.setPrice(productDto.getPrice());
		product.setStock(productDto.getStock());
		product.setStatus(productDto.getStatus());
		product.setNumber(sum);
		product.setImages(productDto.getImages());
		product.setImage(productDto.getImage());
		product.setCategory(category);
		product.setProductSizes(productSizes);
		return product;
	}
}
