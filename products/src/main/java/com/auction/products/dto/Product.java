package com.auction.products.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Product {

  @Id
  @GeneratedValue
  private long id;

  @NotBlank(message = "Product name is required")
  private String name;

  private String shortDescription;

  @Column(columnDefinition = "TEXT")
  private String detailedDescription;

  @NotBlank(message = "Product category is required")
  private String category;

  @Min(value = 0, message = "Starting price must be a non negative value")
  private Float startingPrice;

  @Future(message = "Bid end date must be in the future")
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date bidEndDate;

  @Valid
  private String sellerName;

  @Valid
  private String sellerCity;

  @Valid
  private String sellerEmailAddress;
}
