package com.weather.application;

import com.weather.application.repository.LocationAvgTempRepository;
import com.weather.application.service.LocationAvgTempService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootTest
@EnableEurekaClient
class ApplicationTests {



  @Test
  void contextLoads() {
  }

}
