package com.weather.gateway.configuration;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableEurekaClient
public class GatewayConfig {


  @Bean
  public RouteLocator myRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("weather", r -> r.path("/weather/**")
            .and()
            .method("GET", "POST", "DELETE", "PATCH", "PUT")
            .filters(f ->
                f.retry(rc -> {
                  rc.setRouteId("weather");
                  rc.setMethods(PATCH, POST, DELETE, PUT);
                  rc.setRetries(5);
                }))
            .uri("lb://weather")

        )
        .build();
  }

  @Bean
  public EurekaClientConfigBean eurekaConfigs() {
    Map<String, String> defaultZone = new HashMap<>();
    defaultZone.put("defaultZone", "http://localhost:5001/eureka");
    EurekaClientConfigBean bean = new EurekaClientConfigBean();
    bean.setRegisterWithEureka(true);
    bean.setFetchRegistry(true);
    bean.setServiceUrl(defaultZone);
    return bean;
  }




  @Bean
  @Primary
  public WebEndpointProperties actuatorConfig() {
    WebEndpointProperties bean = new WebEndpointProperties();
    bean.getExposure().getInclude().add("*");
    bean.getDiscovery().setEnabled(true);
    return bean;
  }




}
