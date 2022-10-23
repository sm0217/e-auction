package com.auction.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class UserConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/oauth/**").permitAll()
        .antMatchers("/actuator/**").permitAll()
        .antMatchers("/h2-console").permitAll()
        .antMatchers(HttpMethod.POST, "/users**").permitAll()
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .antMatchers("/**")
        .permitAll();
  }

//  @Bean
//  public UserDetailsService userDetailsService() {
//    UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//    UserDetails user = User.withUsername("sherlock.111@test.com")
//        .password("12345").authorities("read")
//        .build();
//    userDetailsManager.createUser(user);
//    return userDetailsManager;
//  }

  @Bean
  public PasswordEncoder userPasswordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}