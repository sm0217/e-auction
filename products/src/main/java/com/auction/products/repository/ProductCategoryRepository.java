package com.auction.products.repository;

import com.auction.products.dto.Product;
import com.auction.products.dto.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {}
