package com.shopping.vn.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.shopping.vn.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findRoleByRoleName(String name);

  @Query(value = "select r.id,r.role_name from role r where r.role_name=:roleName",
      nativeQuery = true)
  List<Role> readAll();
}
