package com.shopping.vn.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RuntimeExceptionHandling extends RuntimeException {

  private static final long serialVersionUID = 6434390107531729321L;

  public RuntimeExceptionHandling(String message) {
    super(message);

  }


}
