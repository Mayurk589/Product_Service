package com.tcs.tcskart.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	private ProductRepository productRepository;
	public static final int MINIMUM_PRODUCT_STOCK_QUANTITY = 5;
	@Autowired
	private AdminNotificationService notifier;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	// Add Product
	public Product addProduct(String productJson, List<MultipartFile> files) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Product product = mapper.readValue(productJson, Product.class);
		if (product.getCreatedAt() == null) {
			product.setCreatedAt(LocalDateTime.now());
		}
		List<ProductImages> imageList = new ArrayList<>();
		for (MultipartFile file : files) {
			imageList.add(new ProductImages(file.getOriginalFilename(), file.getContentType(), file.getBytes()));
		}

		if (product.getStockQuantity() < MINIMUM_PRODUCT_STOCK_QUANTITY) {
			notifier.notifyLowStock(product);
		}
		return productRepository.save(product);
	}

	// Get All Products
	public Iterable<Product> getAllProducts() {
		return productRepository.findAll();
	}

	// Update Product
	public Product updateProductById(long id, Product newProductDetails, List<MultipartFile> newFiles)
			throws IOException {

		Product productDetails = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product " + id + " not found"));

		productDetails.setName(newProductDetails.getName());
		productDetails.setDescription(newProductDetails.getDescription());
		productDetails.setPrice(newProductDetails.getPrice());
		productDetails.setCategory(newProductDetails.getCategory());
		productDetails.setStockQuantity(newProductDetails.getStockQuantity());

		productDetails.getImages().clear();
		for (MultipartFile f : newFiles) {
			productDetails.getImages()
					.add(new ProductImages(f.getOriginalFilename(), f.getContentType(), f.getBytes()));
		}

		if (productDetails.getStockQuantity() < MINIMUM_PRODUCT_STOCK_QUANTITY) {
			notifier.notifyLowStock(productDetails);
		}
		return productRepository.save(productDetails);
	}

	// Delete Product
	public void deleteProductById(long id) {
		if (!productRepository.existsById(id)) {
			throw new EntityNotFoundException("Product with ID " + id + " not found");
		}
		productRepository.deleteById(id);
	}

	// GetProductById
	public Map<String, Object> getProductById(long id, HttpServletRequest request) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

		List<String> imageUrls = new ArrayList<>();
		for (int i = 0; i < product.getImages().size(); i++) {
			imageUrls.add(baseUrl + "/products/" + id + "/image/" + i);
		}

		Map<String, Object> response = new LinkedHashMap<>();
		ProductDto productResponse = new ProductDto(product.getId(), product.getName(), product.getDescription(),
				product.getPrice(), product.getCategory(), product.getStockQuantity(), product.getCreatedAt(),
				imageUrls);
		response.put("product", productResponse);

		return response;
	}

	public ResponseEntity<byte[]> getProductImageById(long id, int index) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));
		if (index < 0 || index >= product.getImages().size()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		ProductImages image = product.getImages().get(index);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + image.getFilename())
				.contentType(MediaType.parseMediaType(image.getContentType())).body(image.getData());
	}
	
	//lowStock Service
	public List<Product> getLowStockProducts() {
		return productRepository.findByStockQuantityLessThan(MINIMUM_PRODUCT_STOCK_QUANTITY);
	}
	
	

}
