package com.spring6.ecommerce.repository;


import com.spring6.ecommerce.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

//public interface BrandRepository extends PagingAndSortingRepository<Brand, Integer> {
public interface BrandRepository extends JpaRepository<Brand, Integer> {

}
