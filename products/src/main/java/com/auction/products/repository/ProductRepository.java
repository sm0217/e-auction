package com.auction.products.repository;

import com.auction.products.dto.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findAllBySellerEmailAddress(String sellerEmailAddress);
}
