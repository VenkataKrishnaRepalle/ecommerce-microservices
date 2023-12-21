package com.pm.spring.ema.category.model.dao.categoryDAO;

import com.pm.spring.ema.category.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryDAO {
    List<Category> findAll();

    Optional<Category> findById(UUID id);

    Category save(Category category);

    void delete(Category category);

    void deleteById(UUID id);

    Optional<Category> findByName(String name);

    Page<Category> findAllByPage(String searchKeyword, Pageable pageable);

    Page<Category> findAll(Pageable pageable);

    long count();

}
