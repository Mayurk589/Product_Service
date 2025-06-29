package com.tcs.tcskart.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.tcskart.bean.Product;
import com.tcs.tcskart.bean.ProductImages;
import com.tcs.tcskart.dto.ProductDto;
import com.tcs.tcskart.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProductService {
	
	public static final int MINIMUM_PRODUCT_STOCK_QUANTITY = 5;
	
	@Autowired
	private AdminNotificationService notifier;
	
	private ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	
	
	public Product addProduct(Product product) throws IOException {
		
		if (product.getCreatedAt() == null) {
			product.setCreatedAt(LocalDateTime.now());
		}

		if (product.getStockQuantity() < MINIMUM_PRODUCT_STOCK_QUANTITY) {
			notifier.notifyLowStock(product);
		}
		return productRepository.save(product);
	}

	
	// Get All Products
	public List<Product> getAllProducts() {
		
		List<Product> products = productRepository.findAll();
		
		return products;
	}

	// Update Product
	public Product updateProductById(Product updateProduct) throws IOException {
		
		if (!productRepository.existsById(updateProduct.getId())) {
			throw new EntityNotFoundException("Product with ID " + updateProduct.getId() + " not found");
		}

//		if (updateProduct.getCreatedAt() == null) {
//			updateProduct.setCreatedAt(LocalDateTime.now());
//		}

		if (updateProduct.getStockQuantity() < MINIMUM_PRODUCT_STOCK_QUANTITY) {
			notifier.notifyLowStock(updateProduct);
		}
		return productRepository.save(updateProduct); 
		
	}

	// Delete Product
	public boolean deleteProductById(long id) {
		
		if (!productRepository.existsById(id)) {
			throw new EntityNotFoundException("Product with ID " + id + " not found");
		}
		
		productRepository.deleteById(id);
		
		if (!productRepository.existsById(id)) {
			return true;
		}
		
		return false;
		
	}

	// GetProductById
	public Optional<Product> getProductById(long id) {
		
		return productRepository.findById(id);
		
	}

	
	//lowStock Service
	public List<Product> getLowStockProducts() {
		return productRepository.findByStockQuantityLessThan(MINIMUM_PRODUCT_STOCK_QUANTITY);
	}
	
	

}
