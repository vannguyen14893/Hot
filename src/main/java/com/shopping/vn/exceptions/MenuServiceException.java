package com.shopping.vn.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MenuServiceException extends RuntimeException {

	private static final long serialVersionUID = 6434390107531729321L;
	
	public MenuServiceException(String message) {
		super(message);
		
	}

	
}
