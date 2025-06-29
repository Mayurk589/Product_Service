package com.tcs.nagshakti;


import java.text.ParseException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.tcskart.bean.Product;
import com.tcs.tcskart.controller.ProductController;
import com.tcs.tcskart.service.ProductService;



@WebMvcTest(ProductController.class)

public class ProductControllerTest {
//	@Autowired
//	private MockMvc mockMvc;
//	@MockBean
//	private ProductService accountService;
//	@Autowired
//	private ObjectMapper objectMapper;
//	Product mockProduct;
//	@BeforeEach
//	void setUp() throws ParseException {
//	
//		mockProduct.setName("Oppo Mobile");
//		mockProduct.setDescription("Good Mobile");
//		mockProduct.setPrice(19999.99);
//		mockProduct.setStockQuantity(20);
//		mockProduct.setCategory("Electronics");
//		mockProduct.setCreatedAt(LocalDateTime.now());
//		mockProduct.setImages(null);
//
//	}
////	@Test
////	void test() throws JsonProcessingException, Exception {
////		Product mockProduct = new Product("Shri@2000", "Paltan@2025");
////
////		when(productService.addProduct(any(Product.class))).thenReturn(mockProduct);
////		mockMvc.perform(post("/wallets/signup").contentType(MediaType.APPLICATION_JSON)
////				.content(objectMapper.writeValueAsString(mockProduct))).andExpect(status().isOk())
////				.andExpect(content().string("Successfully registered"));
////		verify(customerService).signUp(any(Customer.class));
////	}
}
