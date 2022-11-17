package com.weather.application.utils;

import com.weather.application.service.LocationAvgTempService;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(AppRunner.class);

  private final LocationAvgTempService service;
  public AppRunner(LocationAvgTempService service) {
    this.service = service;
  }

  @Override
  public void run(String... args) throws Exception {
//    Parallel threads to check that retry config is working properly.
//    CompletableFuture<Double> temp = service.test("var", 33.3);
//    CompletableFuture<Double> temp1 = service.test("var", 33.3);
//    CompletableFuture.allOf(temp, temp1).join();

  }
}
