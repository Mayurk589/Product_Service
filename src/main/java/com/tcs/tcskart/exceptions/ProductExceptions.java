package com.tcs.tcskart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;


@ControllerAdvice
public class ProductExceptions {
	@ExceptionHandler(value = EntityNotFoundException.class)
	public ResponseEntity<Object> NoAccountError(EntityNotFoundException exception){
		
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
}
