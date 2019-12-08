package com.shopping.vn.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shopping.vn.dto.ProductDto;
import com.shopping.vn.dto.ProductSizeDto;
import com.shopping.vn.dto.SizeColorDto;
import com.shopping.vn.dto.SizeDto;
import com.shopping.vn.dto.SortFilterDto;
import com.shopping.vn.entity.Category;
import com.shopping.vn.entity.Color;
import com.shopping.vn.entity.Product;
import com.shopping.vn.entity.ProductSize;
import com.shopping.vn.entity.Size;
import com.shopping.vn.entity.SizeColor;
import com.shopping.vn.exceptions.RuntimeExceptionHandling;
import com.shopping.vn.repository.CategoryRepository;
import com.shopping.vn.repository.ColorRepository;
import com.shopping.vn.repository.ProductRepository;
import com.shopping.vn.repository.ProductSizeRepository;
import com.shopping.vn.repository.SizeColorRepository;
import com.shopping.vn.repository.SizeRepository;
import com.shopping.vn.service.ProductService;
import com.shopping.vn.utils.Constants;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private SizeRepository sizeRepository;
	@Autowired
	private ColorRepository colorRepository;
	@Autowired
	private ProductSizeRepository productSizeRepository;
	@Autowired
	private SizeColorRepository sizeColorRepository;

	@Override
	public List<ProductDto> readAll(SortFilterDto filter) {
		List<Product> products = productRepository.readAll(filter);
		List<ProductDto> productDtos = new ArrayList<>();
		for (Product product : products) {
			productDtos.add(ProductDto.convert(product));
		}

		return productDtos;
	}

	@Override
	@Transactional
	public Product createProduct(ProductDto productDto) {
		Product product = new Product();
		Integer sum = 0;
		Integer sumProduct = 0;
		List<Integer> total = new ArrayList<>();
		List<Integer> totalProduct = new ArrayList<>();
		Category category = categoryRepository.findById(productDto.getCategory().getId())
				.orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.CATEGORY_NOT_FOUND));
		product.setDescription(productDto.getDescription());
		product.setName(productDto.getName());
		product.setLocation(productDto.getLocation());
		product.setPrice(productDto.getPrice());
		product.setStock(productDto.getStock());
		product.setStatus(productDto.getStatus());
		product.setCategory(category);

		List<ProductSize> productSizes = new ArrayList<>();
		SizeDto sizeDto = null;
		Size size = null;
		for (ProductSizeDto productSizeDto : productDto.getProductSizeDtos()) {
			ProductSize productSize = new ProductSize();
			productSize.setNumber(productSizeDto.getNumber());
			size = sizeRepository.findById(productSizeDto.getSizeDto().getId())
					.orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.SIZE_NOT_FOUND));
			sizeDto = productSizeDto.getSizeDto();
			productSize.setSize(size);
			productSize.setProduct(product);

			for (SizeColorDto sizeColorDto : sizeDto.getSizeColorDtos()) {
				SizeColor sizeColor = new SizeColor();
				sizeColor.setNumber(sizeColorDto.getNumber());
				total.add(sizeColorDto.getNumber());
				sum = total.stream().mapToInt(Integer::intValue).sum();
				sizeColor.setSize(size);
				Color color = colorRepository.findById(sizeColorDto.getColorDto().getId())
						.orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.COLOR_NOT_FOUND));
				sizeColor.setColor(color);
				sizeColorRepository.save(sizeColor);
			}
			productSize.setNumber(sum);
			totalProduct.add(productSize.getNumber());
			productSizes.add(productSize);
		}
		product.setProductSizes(productSizes);
		sumProduct = totalProduct.stream().mapToInt(Integer::intValue).sum();
		product.setNumber(sumProduct);
		productRepository.save(product);
		return product;
	}

	@Override
	public BigDecimal updatePriceSale(Long id, BigDecimal priceSale) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.PRODUCT_NOT_FOUND));
		product.setPriceSale(priceSale);
		productRepository.save(product);
		return priceSale;
	}

	@Override
	public ProductDto detailProductDto(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.PRODUCT_NOT_FOUND));

		return ProductDto.convert(product);
	}

	@Override
	public Boolean deleteProduct(List<Long> ids) {
		for (Long id : ids) {
			Product product = productRepository.getProductByID(id);
			if (product == null)
				throw new RuntimeExceptionHandling(Constants.MESSENGER.PRODUCT_NOT_FOUND);
			product.setStatus(0);
			productRepository.save(product);
		}
		return true;
	}

	@Override
	public Product updateProduct(ProductDto productDto) {
		Product product = productRepository.findById(productDto.getId())
				.orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.PRODUCT_NOT_FOUND));
		Integer sum = 0;
		Integer sumProduct = 0;
		List<Integer> total = new ArrayList<>();
		List<Integer> totalProduct = new ArrayList<>();
		Category category = categoryRepository.findById(productDto.getCategory().getId())
				.orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.CATEGORY_NOT_FOUND));

		product.setId(productDto.getId());
		product.setDescription(productDto.getDescription());
		product.setName(productDto.getName());
		product.setLocation(productDto.getLocation());
		product.setPrice(productDto.getPrice());
		product.setStock(productDto.getStock());
		product.setStatus(productDto.getStatus());
		product.setCategory(category);

		List<ProductSize> productSizes = new ArrayList<>();
		SizeDto sizeDto = null;
		Size size = null;

		product.getProductSizes().clear();
		for (ProductSizeDto productSizeDto : productDto.getProductSizeDtos()) {
			ProductSize productSize = productSizeRepository.findById(productSizeDto.getId())
					.orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.PRODUCT_SIZE_NOT_FOUND));
			productSize.setId(productSizeDto.getId());
			productSize.setNumber(productSizeDto.getNumber());
			size = sizeRepository.findById(productSizeDto.getSizeDto().getId())
					.orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.SIZE_NOT_FOUND));
			sizeDto = productSizeDto.getSizeDto();
			productSize.setSize(size);
			productSize.setProduct(product);
			size.getSizeColors().clear();

			for (SizeColorDto sizeColorDto : sizeDto.getSizeColorDtos()) {
				SizeColor sizeColor = sizeColorRepository.findById(sizeColorDto.getId())
						.orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.SIZE_COLOR_NOT_FOUND));
				sizeColor.setId(sizeColorDto.getId());
				sizeColor.setNumber(sizeColorDto.getNumber());
				total.add(sizeColorDto.getNumber());
				sum = total.stream().mapToInt(Integer::intValue).sum();
				sizeColor.setSize(size);
				Color color = colorRepository.findById(sizeColorDto.getColorDto().getId())
						.orElseThrow(() -> new RuntimeExceptionHandling(Constants.MESSENGER.COLOR_NOT_FOUND));
				sizeColor.setColor(color);
				sizeColorRepository.save(sizeColor);
			}
			productSize.setNumber(sum);
			totalProduct.add(productSize.getNumber());
			productSizes.add(productSize);
		}
		product.setProductSizes(productSizes);
		sumProduct = totalProduct.stream().mapToInt(Integer::intValue).sum();
		product.setNumber(sumProduct);
		productRepository.save(product);
		return product;
	}

	@Override
	public Long countProduct(SortFilterDto filter) {
		Long countProduct = productRepository.countProduct(filter);
		return countProduct;
	}

}
