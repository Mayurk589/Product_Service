package com.tcs.tcskart.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ProductDto (
	long id,
    String name,
    String description,
    double price,
    String category,
    int stockQuantity,
    LocalDateTime createdAt,
    List<String> imageUrls){

}
