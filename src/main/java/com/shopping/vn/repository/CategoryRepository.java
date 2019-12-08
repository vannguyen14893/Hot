package com.shopping.vn.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.shopping.vn.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  @Query(value = "select c.name from category c where c.name LIKE CONCAT('%',:name,'%')", nativeQuery = true)
  List<String> readCategory(@Param("name") String name);
}
