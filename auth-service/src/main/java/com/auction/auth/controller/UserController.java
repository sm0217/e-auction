package com.auction.auth.controller;


import com.auction.auth.dao.User;
import com.auction.auth.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/users/{username}")
  public ResponseEntity<User> get(@PathVariable("username") String username,
      @RequestHeader("username") String authUser) {
    if (!authUser.equalsIgnoreCase(username)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not authorized");
    }
    User user = userRepository.findById(username).orElseThrow();
    user.setPassword(null);
    return ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(user);
  }

  @PostMapping("/users")
  public ResponseEntity<User> post(@RequestBody @Valid User user) {
    Optional<User> optional = userRepository.findById(user.getEmailAddress());
    if (optional.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
    }
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .body(userRepository.save(user));
  }
}