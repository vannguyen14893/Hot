package com.shopping.vn.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.shopping.vn.response.ErrorMessages;


@ControllerAdvice
@RestController
public class AppExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(value= {UserServiceException.class})
	public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request) {
		ErrorMessages errorMessage=new ErrorMessages(LocalDateTime.now(),ex.getMessage(),HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(errorMessage,new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(value= {Exception.class})
	public ResponseEntity<Object> handleUserServiceExceptions(Exception ex, WebRequest request) {
		ErrorMessages errorMessage=new ErrorMessages(LocalDateTime.now(),ex.getMessage(),HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
