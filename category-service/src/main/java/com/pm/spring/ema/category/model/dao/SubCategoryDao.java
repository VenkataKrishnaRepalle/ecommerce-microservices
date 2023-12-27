package com.pm.spring.ema.category.model.dao;


import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryDeleteResponseDto;
import com.pm.spring.ema.category.model.entity.Category;
import com.pm.spring.ema.category.model.entity.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubCategoryDao {
    List<SubCategory> findAll();

    Optional<SubCategory> findById(UUID id);

    SubCategory save(SubCategory subCategory);

    void delete(SubCategory subCategory);

    SubCategoryDeleteResponseDto deleteById(UUID id);

    Optional<SubCategory> findByName(String name);

    Page<SubCategory> findAllByPage(String searchKeyword, Pageable pageable);

    Page<SubCategory> findAll(Pageable pageable);

    List<SubCategory> findByCategory(Category category);

}
