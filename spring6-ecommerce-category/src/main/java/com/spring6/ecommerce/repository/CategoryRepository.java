package com.spring6.ecommerce.repository;

import com.spring6.ecommerce.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CategoryRepository extends PagingAndSortingRepository<Category, UUID>{

}
