package com.auction.auth.controller;

import com.auction.auth.dao.User;
import com.auction.auth.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userOptional = userRepository.findById(username);
    User user = userOptional.orElseThrow();
    UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(
            user.getEmailAddress())
        .password(user.getPassword()).roles(user.getRole()).build();
    return userDetails;
  }
}
