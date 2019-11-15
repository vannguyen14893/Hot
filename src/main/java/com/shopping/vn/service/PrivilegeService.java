package com.shopping.vn.service;

import java.util.List;
import com.shopping.vn.dto.PrivilegeDto;
import com.shopping.vn.entity.Privilege;

public interface PrivilegeService {
  List<PrivilegeDto> readAll(String name, List<Long> roleIds);

  PrivilegeDto detailPrivilege(Long id);

  Privilege addPrivilege(PrivilegeDto privilegeDto);

  Privilege updatePrivilege(PrivilegeDto privilegeDto);

  boolean deletePrivilege(Long id);
}
