package com.auction.products.service;

import com.auction.products.client.BuyersClient;
import com.auction.products.dto.Bid;
import com.auction.products.dto.Product;
import com.auction.products.dto.ProductCategory;
import com.auction.products.repository.ProductCategoryRepository;
import com.auction.products.repository.ProductRepository;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class ProductService {

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private ProductCategoryRepository productCategoryRepository;
  @Autowired
  private BuyersClient buyersClient;

  public List<Product> getAllProducts(String username) {
    return productRepository.findAllBySellerEmailAddress(username);
  }

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  public Product getProductById(Long productId) {
    log.info("Get product details for id {}", productId);
    return productRepository.findById(productId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            "product not found"));
  }

  public List<Bid> getAllBidsByProductId(Long productId) {
    return buyersClient.findAllByProductId(productId);
  }

  public List<Bid> getAllBidsByProductIdAndUsername(Long productId, String username) {
    return buyersClient.findAllByProductIdAndUsername(productId, username);
  }

  public Product createProduct(@Valid Product product) {
    validateProductCategory(product);
    return productRepository.save(product);
  }

  public List<ProductCategory> getProductCategories() {
    return productCategoryRepository.findAll();
  }

  private void validateProductCategory(Product product) {
    boolean matches =
        getProductCategories().stream()
            .map(ProductCategory::getName)
            .anyMatch(category -> product.getCategory().equalsIgnoreCase(category));
    if (!matches) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product category invalid");
    }
  }


  public void deleteProduct(Long productId) {
    productRepository.deleteById(productId);
  }
}
