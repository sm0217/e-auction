package com.auction.sales.repository;

import com.auction.sales.dto.Bid;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {

  Bid findByEmailAddressAndProductId(String emailAddress, Long productId);

  List<Bid> findByProductId(Long productId);
}
