package com.auction.products.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "PRODUCT_CATEGORY")
public class ProductCategory {
  @Id private long id;

  private String name;
}
