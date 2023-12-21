package com.pm.spring.ema.category.model.dao.subCategoryDAO;

import com.pm.spring.ema.category.model.entity.Category;
import com.pm.spring.ema.category.model.entity.SubCategory;
import com.pm.spring.ema.category.model.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class SubCategoryDAOImpl implements SubCategoryDAO {
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public List<SubCategory> findAll() {
        return subCategoryRepository.findAll();
    }

    @Override
    public Optional<SubCategory> findById(UUID id) {
        return subCategoryRepository.findById(id);
    }

    @Override
    public SubCategory save(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    @Override
    public void delete(SubCategory subCategory) {
        subCategoryRepository.delete(subCategory);

    }

    @Override
    public void deleteById(UUID id) {
        subCategoryRepository.deleteById(id);
    }

    @Override
    public Optional<SubCategory> findByName(String name) {
        return subCategoryRepository.findByName(name);
    }

    @Override
    public Page<SubCategory> findAllByPage(String searchKeyword, Pageable pageable) {
        return subCategoryRepository.findAllByPage(searchKeyword, pageable);
    }

    @Override
    public Page<SubCategory> findAll(Pageable pageable) {
        return subCategoryRepository.findAll(pageable);
    }

    @Override
    public List<SubCategory> findByCategory(Category category) {
        return subCategoryRepository.findByCategory(category);
    }
}
