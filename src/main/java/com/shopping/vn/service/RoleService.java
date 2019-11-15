package com.shopping.vn.service;

import java.util.List;
import com.shopping.vn.dto.RoleDto;

public interface RoleService {
  List<RoleDto> readAll();

  RoleDto detailRole(Long id);

  void addRole(RoleDto roleDto);

  void updateRole(RoleDto roleDto);

  void deleteRole(Long id);
}
