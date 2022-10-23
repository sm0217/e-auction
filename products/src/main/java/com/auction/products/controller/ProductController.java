package com.auction.products.controller;

import com.auction.products.dto.Bid;
import com.auction.products.dto.Product;
import com.auction.products.dto.ProductCategory;
import com.auction.products.service.ProductService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping("/products")
  public ResponseEntity<List<Product>> getAll(@RequestHeader("username") String username,
      @RequestHeader("role") String role) {
    List<Product> products = role.equals("ROLE_SELLER") ? productService.getAllProducts(username)
        : productService.getAllProducts();
    return ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(products);
  }

  @GetMapping("/products/{id}")
  public ResponseEntity<Product> findById(@PathVariable Long id) {
    Product product = productService.getProductById(id);
    return ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(product);
  }

  @PostMapping("/seller/add-product")
  public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product,
      @RequestHeader("username") String username) {
    Product createdProduct = productService.createProduct(product);
    return ResponseEntity.status(HttpStatus.CREATED)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(createdProduct);
  }

  @DeleteMapping("/seller/delete/{id}")
  public ResponseEntity deleteProduct(@PathVariable Long id) {
    List<Bid> bids = getBidsByProductId(id);
    if (!bids.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Cannot delete product with active bid");
    }
    productService.deleteProduct(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping("/seller/show-bids/{productId}")
  public ResponseEntity<List<Bid>> getAllBidsByProductId(@PathVariable Long productId,
      @RequestHeader("username") String username, @RequestHeader("role") String role) {
    List<Bid> bids = role.equals("ROLE_SELLER") ? getBidsByProductId(productId)
        : productService.getAllBidsByProductIdAndUsername(productId, username);
    return ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(bids);
  }

  @GetMapping("/seller/product-categories")
  public ResponseEntity<List<ProductCategory>> getProductCategories(@PathVariable Long productId) {
    return ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(productService.getProductCategories());
  }

  private List<Bid> getBidsByProductId(Long productId) {
    return productService.getAllBidsByProductId(productId);
  }
}
