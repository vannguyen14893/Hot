package com.shopping.vn.dto;

import lombok.Data;

@Data
public class SortFilterDto {
	private Long roleId;
	private Integer status;
	private String fullName;
	private String email;
	private String startDate;
	private String endDate;
	private Boolean sort;
	private String sortName;
	private Integer page = 1;
	private Integer pageSize = 5;
    private String permission;
}
