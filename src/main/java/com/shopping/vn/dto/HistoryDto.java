package com.shopping.vn.dto;

import java.util.Date;

import lombok.Data;

@Data
public class HistoryDto {
	private Long id;
	private String descrition;

	private Date createdDate;

	private Date updatedDate;
	private Date deletedDate;
	private UserDto createdBy;
	private UserDto updatedBy;
	private UserDto deletedBy;
}
