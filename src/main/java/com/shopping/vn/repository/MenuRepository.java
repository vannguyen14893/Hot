package com.shopping.vn.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.shopping.vn.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
  @Query(value = "select m.id,m.name from menu m join roles_menus rm on m.id=rm.menu_id "
      + "join role r on r.id=rm.role_id join users_roles ur on r.id=ur.role_id "
      + "join user u on ur.user_id=u.id where u.id= :userId and m.parent_id=0 ", nativeQuery = true)
  List<Object[]> readMenuByUser(Long userId);

  @Query(value = "select m.id ,m.name,m.parent_id from menu m where m.parent_id=0",
      nativeQuery = true)
  List<Menu> readAllbyParent();

  @Query(value = "select m.id ,m.name,m.parent_id from menu m where m.parent_id=:menuId",
      nativeQuery = true)
  List<Menu> readAllByMenuId(@Param("menuId") Long menuId);
}
