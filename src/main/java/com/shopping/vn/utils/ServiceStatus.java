package com.shopping.vn.utils;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ServiceStatus implements Serializable {
  LOGIN_SUCCESS(0, "Login success", 200),
  ADD_SUCCESS(1, "Create success", 200),
  DELETE_SUCCESS(2,"Delete success", 200),
  UPDATE_SUCCESS(3, "Update success", 200),
  SIGN_UP_URL(4,"Registration success",200),
  VALUE_DONOT_EXIST(5, "Value do not exist", 400),
  VALUE_CAN_USE(6, "Value can use",400),
  NO_DATA(7, "No data", 400),
  USER_NOT_FOUND(8, "User not found",400),
  ROLE_NOT_FOUND(9, "Role not found", 400);

  private int id;

  private String message;

  private int status;

  public int getId() {
    return id;
  }

  public String getMessage() {
    return message;
  }

  public int getStatus() {
    return status;
  }

  ServiceStatus(int id, String message, int status) {
    this.id = id;
    this.message = message;
    this.status = status;
  }

}
