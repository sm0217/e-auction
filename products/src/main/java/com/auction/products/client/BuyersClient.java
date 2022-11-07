package com.auction.products.client;

import com.auction.products.dto.Bid;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sales-service")
public interface BuyersClient {

  @GetMapping("/e-auction/api/v1/buyer/bids/{productId}")
  List<Bid> findAllByProductId(@PathVariable("productId") Long productId);

  @GetMapping("/e-auction/api/v1/buyer/bids/{username}/{productId}")
  List<Bid> findAllByProductIdAndUsername(@PathVariable("productId") Long productId, @PathVariable("username") String username);
}
