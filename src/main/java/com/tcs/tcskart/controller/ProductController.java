package com.tcs.tcskart.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.tcskart.bean.Product;
import com.tcs.tcskart.service.ProductService;


@RestController
@RequestMapping("api/products")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/addProduct")
	public ResponseEntity<Map<String, Object>> addProduct(@RequestBody Product product) throws IOException {

		Map<String, Object> response = new HashMap<>();

		Product savedProduct = productService.addProduct(product);

		if (savedProduct == null) {

			response.put("status", "error");
			response.put("message", "Product is empty. error while adding...");

			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		response.put("status", "success");
		response.put("message", "Product Added successfully!");
		response.put("data", product);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/getAllProducts")
	public ResponseEntity<Map<String, Object>> getAllProducts() {

		List<Product> products = productService.getAllProducts();

		Map<String, Object> response = new HashMap<>();

		if (products == null) {
			response.put("success", true);
			response.put("message", "No products Found");
		}

		else {
			response.put("success", true);
			response.put("message", products);
		}

		return ResponseEntity.ok(response);
	}

	// Update Products-10
	@PutMapping("/update/{productId}")
	public ResponseEntity<Map<String, Object>> updateProductById(@RequestBody Product product) throws IOException {

		Map<String, Object> response = new HashMap<>();

		Product updatedProduct = productService.updateProductById(product);

		if (updatedProduct == null) {

			response.put("status", "error");
			response.put("message", "Product is empty. error while adding...");

			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		response.put("status", "success");
		response.put("message", "Product Added successfully!");
		response.put("data", updatedProduct);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	// Delete Product by ID-11
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<Map<String, Object>> deleteProductById(@PathVariable long productId) {
		boolean deletedProduct = productService.deleteProductById(productId);

		Map<String, Object> response = new HashMap<>();

		if (!deletedProduct) {
			response.put("success", false);
			response.put("message", "Product Deletion Failed...");
		}

		else {
			response.put("success", true);
			response.put("message", "Product Deleted Successfully...");
		}

		return ResponseEntity.ok(response);

	}

	// Get Product By ID-7
	@GetMapping("/getProduct/{productId}")
	public ResponseEntity<Map<String, Object>> getProducById(@PathVariable Long productId) {
		
		Optional<Product> product = productService.getProductById(productId);

		Map<String, Object> response = new HashMap<>();

		if (product.isEmpty()) {
			response.put("success", true);
			response.put("message", "No Product Found");
		}

		else {
			response.put("success", true);
			response.put("message", product);
		}

		return ResponseEntity.ok(response);

	}


	// Notify admin for low-stock-33
	@GetMapping("/low-stock")
	public ResponseEntity<Map<String, Object>> getLowStockProducts() {
		
		List<Product> product = productService.getLowStockProducts();

		Map<String, Object> response = new HashMap<>();

		if (product.isEmpty()) {
			response.put("success", true);
			response.put("message", "No Products are Found with low Stock");
		}

		else {
			response.put("success", true);
			response.put("message", product);
		}

		return ResponseEntity.ok(response);
		
	}

}
