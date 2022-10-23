package com.auction.sales.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
public class Bid {

  @Id
  @GeneratedValue
  private Long id;

  private String emailAddress;

  private Long productId;

  @Column(name = "bid_amount")
  private float bidAmount;
}
