package com.auction.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class SecurityConfig extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/oauth/**").permitAll()
        .antMatchers("/actuator/**").permitAll()
        .antMatchers(HttpMethod.POST,"/users**").permitAll()
        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
        .antMatchers("/**")
        .authenticated();
  }

  @Bean
  public RemoteTokenServices remoteTokenService() {
    RemoteTokenServices tokenService = new RemoteTokenServices();
    tokenService.setCheckTokenEndpointUrl("http://auth-service:8086/oauth/check_token");
    tokenService.setClientId("oauthclient1");
    tokenService.setClientSecret("oauthsecret1");
    return tokenService;
  }
}