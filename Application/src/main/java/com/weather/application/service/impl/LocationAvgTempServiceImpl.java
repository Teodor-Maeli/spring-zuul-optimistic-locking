package com.weather.application.service.impl;

import com.weather.application.aspects.RetryOnFailure;
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
      entity.setSum(entity.getSum() + newEntry.getEntry());
      repository.save(entity);
      return calculateAvg(entity.getCounter(), entity.getSum());
    }
    entity = new LocationAvgTemp(newEntry.getLocation(), newEntry.getEntry());
    repository.save(entity);
    return calculateAvg(entity.getCounter()-1, newEntry.getEntry());
  }

  private Double calculateAvg(int counter, Double sum) {
    Double avg = sum / (counter + 1);
    return avg;
  }

  //Testing retry method execution on locked row
  @Transactional
  @Async
  public CompletableFuture<Double> test(String location, Double newEntry) {
      LocationAvgTemp temp = repository.findByLocation(location);
      temp.setSum(temp.getSum() + newEntry);
      temp.setLocation(location);
      repository.save(temp);
    return null;
  }

  @Transactional
  @Async
  public CompletableFuture<Double> test1(String location, Double newEntry) {
      LocationAvgTemp temp = repository.findByLocation(location);
      temp.setSum(temp.getSum() + newEntry);
      temp.setLocation(location);
      repository.save(temp);
    return null;
  }


}
