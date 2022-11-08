package com.auction.gateway.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.RestTemplate;

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
        .antMatchers(HttpMethod.GET,"/e-auction/api/v1/test123**").permitAll()
        .antMatchers("/**")
        .authenticated();
  }

  @Bean(name = "loadBalancedRestTemplate")
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public RemoteTokenServices remoteTokenService(@Qualifier("loadBalancedRestTemplate") RestTemplate loadBalancedRestTemplate) {
    RemoteTokenServices tokenService = new RemoteTokenServices();
    tokenService.setCheckTokenEndpointUrl("http://auth-service/oauth/check_token");
    tokenService.setClientId("oauthclient1");
    tokenService.setClientSecret("oauthsecret1");
    tokenService.setRestTemplate(loadBalancedRestTemplate);
    return tokenService;
  }
}