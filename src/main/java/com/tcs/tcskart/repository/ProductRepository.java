package com.tcs.tcskart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.tcskart.bean.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByStockQuantityLessThan(int quantity);
	
}
