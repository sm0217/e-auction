package com.auction.sales.service;

import com.auction.sales.client.ProductClient;
import com.auction.sales.dto.Bid;
import com.auction.sales.dto.Product;
import com.auction.sales.repository.BidRepository;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class BiddingService {

  @Autowired
  private BidRepository bidRepository;

  @Autowired
  private ProductClient productClient;

  public Bid placeBid(Bid bid) {
    Product product = getProduct(bid.getProductId());
    if (bid.getBidAmount() > product.getStartingPrice()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Bid amount greater than starting price");
    }
    return bidRepository.save(bid);
  }

  private Product getProduct(Long productId) {
    try {
      return productClient.findById(productId);
    } catch (Exception exception) {
      log.error("Error getting product", exception);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id invalid");
    }
  }

  public Bid updateBid(Long productId, String emailAddress, Float bidAmount) {
    Bid buyer = bidRepository.findByEmailAddressAndProductId(emailAddress, productId);
    buyer.setBidAmount(bidAmount);
    return bidRepository.save(buyer);
  }

  public List<Bid> findAllById(Long productId) {
    return bidRepository.findByProductId(productId);
  }

  public List<Bid> findAllByProductIdAndUsername(Long productId, String username) {
    Bid bid = bidRepository.findByEmailAddressAndProductId(username,
        productId);
    return bid != null ? Collections.singletonList(bid) : Collections.emptyList();
  }
}
