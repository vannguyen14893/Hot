package com.shopping.vn.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.shopping.vn.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  @Query(value = "select * from comment c where c.parent_id=:id ", nativeQuery = true)
  List<Comment> findByParentId(Long id);

  @Query(value = "select * from comment c where c.parent_id=0 and c.product_id= :productId order by c.created_date ",
      nativeQuery = true)
  List<Comment> findByProductAndParentId(Long productId);

  @Query(value = "select count(if(c.id=null,1,0)) from comment c where c.parent_id=:id ",
      nativeQuery = true)
  Long count(Long id);
}
