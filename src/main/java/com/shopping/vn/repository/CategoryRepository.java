package com.shopping.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopping.vn.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
