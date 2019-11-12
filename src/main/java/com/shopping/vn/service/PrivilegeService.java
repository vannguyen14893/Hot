package com.shopping.vn.service;

import java.util.List;

import com.shopping.vn.dto.PrivilegeDto;

public interface PrivilegeService {
	List<PrivilegeDto> readAll(String name,List<Long> roleIds);

	PrivilegeDto detailPrivilege(Long id);

	void addPrivilege(PrivilegeDto privilegeDto);

	void updatePrivilege(PrivilegeDto privilegeDto);

	void deletePrivilege(Long id);
}
