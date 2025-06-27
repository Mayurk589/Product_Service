package com.tcs.tcskart.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn; 

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String name;
	private String description;
	private double price;
	private String category;
	private int stockQuantity;
	private LocalDateTime createdAt;
	
	  @ElementCollection(fetch = FetchType.LAZY)
	    @CollectionTable(name = "product_images",
	                     joinColumns = @JoinColumn(name = "product_id"))
	 private List<ProductImages> images = new ArrayList<>();

	
}
