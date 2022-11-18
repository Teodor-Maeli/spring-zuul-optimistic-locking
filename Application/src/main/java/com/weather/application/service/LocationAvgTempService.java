package com.weather.application.service;
import com.weather.application.dto.LocationAvgTempDto;
import java.util.concurrent.CompletableFuture;


public interface LocationAvgTempService {

  Double updateAverageTemperature(LocationAvgTempDto entity);
  CompletableFuture<Double> test(String location, Double newEntry);
  CompletableFuture<Double> test1(String location, Double newEntry);
}
