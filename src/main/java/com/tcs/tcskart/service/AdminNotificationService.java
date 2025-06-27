package com.tcs.tcskart.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tcs.tcskart.bean.Product;

@Service
@AllArgsConstructor
public class AdminNotificationService {

	private final JavaMailSender mailSender;

	public void  notifyLowStock(Product product) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("shriharisekar@gmail.com");
		message.setSubject("Low Stock Alert: " + product.getName());
		message.setText("The stock for product '" + product.getName() + "' is only " + product.getStockQuantity()
				+ ". Please make sure it is more than 5.");
		mailSender.send(message);
	}
}
