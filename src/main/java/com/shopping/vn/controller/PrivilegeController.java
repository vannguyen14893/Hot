package com.shopping.vn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.vn.dto.PrivilegeDto;
import com.shopping.vn.service.PrivilegeService;
import com.shopping.vn.utils.Constants;

@RestController
public class PrivilegeController {
	@Autowired
	private PrivilegeService privilegeService;

	@PostMapping(value = "/read-all-priviege")
	public ResponseEntity<?> readAll(@RequestParam("name") String name, @RequestBody List<Long> roleIds) {
		try {
			List<PrivilegeDto> privilegeDtos = privilegeService.readAll(name, roleIds);
			if (CollectionUtils.isEmpty(privilegeDtos)) {
				return new ResponseEntity<>(Constants.MESSENGER.NO_DATA, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(privilegeDtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
