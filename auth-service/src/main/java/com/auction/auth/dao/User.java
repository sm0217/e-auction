package com.auction.auth.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Entity(name = "users")
public class User {

  @NotBlank(message = "Email address must not be blank")
  @Email
  @Id
  private String emailAddress;

  @Length(min = 5, max = 30, message = "FirstName field must be between 5 and 30 characters")
  private String firstName;

  @Length(min = 5, max = 30, message = "Surname field must be between 5 and 30 characters")
  private String surname;

  private String address;
  private String city;
  private String state;
  private String pin;

  @Pattern(regexp = "[0-9]{10}", message = "Phone number must contain only 10 digits")
  private String phoneNumber;

  private String role;
  private String password;
}