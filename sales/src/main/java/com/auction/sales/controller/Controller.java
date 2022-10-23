package com.auction.sales.controller;

import com.auction.sales.dto.Bid;
import com.auction.sales.service.BiddingService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  @Autowired
  private BiddingService biddingService;

  @PostMapping("/buyer/place-bid")
  public ResponseEntity<Bid> placeBid(@Valid @RequestBody Bid bid,
      @RequestHeader("username") String username) {
    bid.setEmailAddress(username);
    return ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(biddingService.placeBid(bid));
  }

  @GetMapping("/buyer/bids/{productId}")
  public ResponseEntity<List<Bid>> placeBid(@PathVariable Long productId) {
    return ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(biddingService.findAllById(productId));
  }

  @GetMapping("/buyer/bids/{username}/{productId}")
  public ResponseEntity<List<Bid>> placeBid(@PathVariable Long productId,
      @PathVariable String username) {
    List<Bid> bids =
        username != null ? biddingService.findAllByProductIdAndUsername(productId, username)
            : biddingService.findAllById(productId);
    return ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(bids);
  }


  @PatchMapping("/buyer/update-bid/{productId}/{buyerEmailld}/{newBidAmount}")
  public ResponseEntity<Bid> addProduct(@PathVariable Long productId,
      @PathVariable String buyerEmailld, @PathVariable Float newBidAmount) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(biddingService.updateBid(productId, buyerEmailld, newBidAmount));
  }

}
