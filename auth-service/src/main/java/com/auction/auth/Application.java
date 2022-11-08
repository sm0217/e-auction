package com.auction.auth;

import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
public class Application {

  public static void main(String[] args) {

    SpringApplication.run(Application.class, args);
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
    config.setNonSecurePort(8086);
    return config;
  }
}