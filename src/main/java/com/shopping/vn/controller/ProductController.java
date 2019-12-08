package com.shopping.vn.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shopping.vn.dto.ProductDto;
import com.shopping.vn.dto.SortFilterDto;
import com.shopping.vn.entity.Category;
import com.shopping.vn.exceptions.RuntimeExceptionHandling;
import com.shopping.vn.repository.CategoryRepository;
import com.shopping.vn.service.MapValidationErrorService;
import com.shopping.vn.service.ProductService;
import com.shopping.vn.utils.ServiceStatus;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
    @Autowired
    private CategoryRepository categoryRepository;
	@PostMapping(value = "/list-product")
	public ResponseEntity<?> readAll(@RequestBody SortFilterDto filter) {
		List<ProductDto> productDtos = productService.readAll(filter);
		if (productDtos.isEmpty()) {
			return new ResponseEntity<>(ServiceStatus.NO_DATA, HttpStatus.OK);
		}
		return new ResponseEntity<>(productDtos, HttpStatus.OK);
	}
	@PostMapping(value = "/list-category")
    public ResponseEntity<?> readCategory(@RequestBody String name) {
        List<String> categories = categoryRepository.readCategory(name);
        if (categories.isEmpty()) {
            return new ResponseEntity<>(ServiceStatus.NO_DATA, HttpStatus.OK);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
	@PostMapping(value = "/add-product")
	public ResponseEntity<?> create(@Valid @RequestBody ProductDto productDto, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		productService.createProduct(productDto);
		return new ResponseEntity<>(ServiceStatus.ADD_SUCCESS, HttpStatus.CREATED);
	}

	@PostMapping(value = "/delete-product")
	public ResponseEntity<?> deleteProduct(@RequestBody List<Long> ids) {
		if (CollectionUtils.isEmpty(ids))
			throw new RuntimeExceptionHandling("No data");
		productService.deleteProduct(ids);
		return new ResponseEntity<>(ServiceStatus.DELETE_SUCCESS, HttpStatus.OK);
	}

	@PostMapping(value = "/detail-product/{id}")
	public ResponseEntity<ProductDto> detailProduct(@PathVariable Long id) {
		ProductDto detailProductDto = productService.detailProductDto(id);
		return new ResponseEntity<>(detailProductDto, HttpStatus.OK);
	}

	@PutMapping(value = "/update-price/{id}")
	public ResponseEntity<?> updateSalePrice(@PathVariable Long id, @RequestBody BigDecimal priceSale) {
		productService.updatePriceSale(id, priceSale);
		return new ResponseEntity<>(ServiceStatus.UPDATE_SUCCESS, HttpStatus.OK);
	}

	@PutMapping(value = "/update-product")
	public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDto productDto, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		productService.updateProduct(productDto);
		return new ResponseEntity<>(ServiceStatus.UPDATE_SUCCESS, HttpStatus.OK);
	}

	@PostMapping(value = "/count-product")
	public ResponseEntity<Long> countProduct(@RequestBody SortFilterDto filter) {
		Long countProduct = productService.countProduct(filter);
		return new ResponseEntity<>(countProduct, HttpStatus.OK);
	}
}
