package com.shopping.vn.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.shopping.vn.entity.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
  @Query(value = "select p.name from privilege p join roles_privileges rp on p.id=rp.privilege_id "
      + "join role r on r.id=rp.role_id join users_roles ur on r.id=ur.role_id "
      + "join user u on ur.user_id=u.id where u.email= :email", nativeQuery = true)
  List<Object[]> readAllByUser(@Param("email") String email);

  @Query(
      value = "select p.id,p.name from privilege p join roles_privileges rp on p.id=rp.privilege_id "
          + "join role r on r.id=rp.role_id where 1=1 and (:name is null or (p.name like %:name%)) and (:roleIds is null or r.id in (:roleIds)) ",
      nativeQuery = true)
  List<Object[]> readAll(@Param("name") String name, @Param("roleIds") List<Long> roleIds);
}
