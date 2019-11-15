package com.shopping.vn.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shopping.vn.dto.MenuDto;
import com.shopping.vn.service.MenuService;
import io.swagger.annotations.ApiOperation;

@RestController
public class MenuController {
  @Autowired
  MenuService menuService;
  @ApiOperation(value = "view menu all menu")
  @GetMapping(value = "/read-all-menu")
  public ResponseEntity<List<MenuDto>> readAll() {
    List<MenuDto> menuDtos = menuService.readAll();
    return new ResponseEntity<>(menuDtos, HttpStatus.OK);
  }
}
