package com.shopping.vn.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestModel {
  @NotBlank(message = "Email cannot be blank")
  private String email;
  @NotBlank(message = "Password cannot be blank")
  private String password;

}
