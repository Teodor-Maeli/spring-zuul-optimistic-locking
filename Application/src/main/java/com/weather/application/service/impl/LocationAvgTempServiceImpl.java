package com.weather.application.service.impl;

import com.weather.application.domain.LocationAvgTemp;
import com.weather.application.dto.LocationAvgTempDto;
import com.weather.application.repository.LocationAvgTempRepository;
import com.weather.application.service.LocationAvgTempService;
import java.util.concurrent.CompletableFuture;
import javax.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LocationAvgTempServiceImpl implements LocationAvgTempService {
  private final LocationAvgTempRepository repository;

  public LocationAvgTempServiceImpl(LocationAvgTempRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public Double updateAverageTemperature(LocationAvgTempDto newEntry) {
    LocationAvgTemp entity;
    if (repository.existsByLocation(newEntry.getLocation())) {
      entity = repository.findByLocation(newEntry.getLocation());
      entity.setCounter(entity.getCounter() + 1);
      entity.setSum(entity.getSum() + newEntry.getEntry());
      repository.save(entity);
      return calculateAvg(entity.getCounter(), entity.getSum(), newEntry.getEntry());
    }
    entity = new LocationAvgTemp(newEntry.getLocation(), newEntry.getEntry(), 1);
    repository.save(entity);
    return calculateAvg(entity.getCounter(), 0.0, newEntry.getEntry());
  }

  private Double calculateAvg(int counter, Double sum, Double newEntry) {
    Double avg = (sum + newEntry) / counter;
    return avg;
  }

  //Testing retry method execution on locked row
  @Transactional
  @Async
  public CompletableFuture<Double> test(String location, Double newEntry){
      LocationAvgTemp temp = repository.findByLocation(location);
      temp.setLocation(location);
      temp.setCounter(temp.getCounter() + 1);
      repository.saveAndFlush(temp);
      return CompletableFuture.completedFuture(
          calculateAvg(temp.getCounter(), temp.getSum(), newEntry));
  }


}
