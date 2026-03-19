package com.pm.spring.ema.category.repository;

import com.pm.spring.ema.category.model.Category;
import com.pm.spring.ema.category.model.SubCategory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {

  Optional<SubCategory> findByName(String name);

  List<SubCategory> findByCategory(Category category);

  @Query("SELECT c FROM SubCategory c WHERE c.name LIKE '%:keyword%'")
  Page<SubCategory> findAllByPage(@Param("keyword") String keyword, Pageable pageable);
}
