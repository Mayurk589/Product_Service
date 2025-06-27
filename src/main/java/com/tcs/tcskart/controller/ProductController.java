package com.tcs.tcskart.controller;

import java.io.IOException;

import java.util.List;
import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.tcskart.bean.Product;
import com.tcs.tcskart.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/products")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	// Add Product with images-9
	@PostMapping("/")
	public ResponseEntity<String> addProduct(@RequestPart("product") String productJson,
			@RequestPart("images") List<MultipartFile> files) throws IOException {

		Product savedProduct=productService.addProduct(productJson,files);
		return ResponseEntity.ok("Product "+savedProduct.getId()+ "added successfully with " + files.size() + " images.");
	}
	

	// Get Products-3
	@GetMapping("/")
	public ResponseEntity<List<Product>> getAllProducts() {
		return ResponseEntity.ok((List<Product>) productService.getAllProducts());
	}


	// Update Products-10
	@PutMapping("/{productId}")

	public ResponseEntity<String> updateProductById(@PathVariable Long productId, @RequestPart("product") String productJson,
			@RequestPart(value = "images", required = false) List<MultipartFile> files) throws IOException {

		Product newProductDetails = new ObjectMapper().readValue(productJson, Product.class);
		productService.updateProductById(productId, newProductDetails, files);
		return ResponseEntity.ok("Product " + productId + " updated successfully");
	}

	// Delete Product by ID-11
	@DeleteMapping("/{productId}")
	public ResponseEntity<String> deleteProductById(@PathVariable long productId) {
		productService.deleteProductById(productId);
		return ResponseEntity.ok("Product deleted successfully with id = " + productId);
	}

	//Get Product By ID-7
	@GetMapping("/{productId}")
	public ResponseEntity<Map<String, Object>> getProducById(@PathVariable Long productId, HttpServletRequest request) {
	   return ResponseEntity.ok(productService.getProductById(productId,request));
	}

	// Get Product Image By ID-7
	@GetMapping("/{productId}/image/{index}")
	public ResponseEntity<byte[]> getImage(@PathVariable Long productId, @PathVariable int index) {
		return productService.getProductImageById(productId, index);
	}

	
	//Notify admin for low-stock-33
	@GetMapping("/low-stock")
	public ResponseEntity<List<Product>> getLowStockProducts() {
		return ResponseEntity.ok(productService.getLowStockProducts());
	}

}
