package com.spring6.ecommerce.service;

import com.spring6.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

//    private final ProductMapper productMapper;
//
//    public List<ProductFindResponseDto> listAll() {
//        System.out.println(productRepository.findAll().get(0));
//        return productRepository.findAll()
//                .stream()
//                .map(productMapper::productToProductFindResponseDto).toList();
//    }
//
//    @Override
//    public ProductFindResponseDto getProductById(UUID productId) throws ProductNotFoundException {
//        Optional<Product> product = productRepository.findById(productId);
//
//        if(product.isPresent()) {
//            return productMapper.productToProductFindResponseDto(product.get());
//        }
//
//        throw new ProductNotFoundException("Could not find any Product with Id " +productId);
//    }
}
