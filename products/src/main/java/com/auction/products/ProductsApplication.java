package com.auction.products;

import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
public class ProductsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductsApplication.class, args);
  }

  @Bean
  public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {

    EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
    String ip = null;
    try {
      ip = InetAddress.getLocalHost().getHostAddress();

    } catch (UnknownHostException e) {
      log.error("Eureka reg", e);
    }

    config.setIpAddress(ip);
    config.setPreferIpAddress(true);
    config.setNonSecurePortEnabled(true);
    config.setNonSecurePort(8081);
    return config;
  }

}
