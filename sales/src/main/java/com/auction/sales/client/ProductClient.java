package com.auction.sales.client;

import com.auction.sales.dto.Product;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products")
public interface ProductClient {

  @GetMapping("/e-auction/api/v1/products/{productId}")
  Product findById(@PathVariable("productId") Long productId);
}
