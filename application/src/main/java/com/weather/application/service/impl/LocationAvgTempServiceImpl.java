package com.weather.application.service.impl;

import com.weather.application.domain.LocationAvgTemp;
import com.weather.application.dto.LocationAvgTempDto;
import com.weather.application.repository.LocationAvgTempRepository;
import com.weather.application.service.LocationAvgTempService;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LocationAvgTempServiceImpl implements LocationAvgTempService {

  private static int retryCounter = 0;
  private final LocationAvgTempRepository repository;
  private static final Logger log = LoggerFactory.getLogger(LocationAvgTempServiceImpl.class);

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
      log.info("Version {}",entity.getVersion());
      repository.save(entity);
      log.info("Version {}",entity.getVersion());
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
    log.info("Attempt- {}. Retrying at {}",retryCounter,LocalDateTime.now());
      LocationAvgTemp temp = repository.findByLocation(location);
    log.info("Version: {}",temp.getVersion());
      retryCounter++;
      temp.setLocation(location);
      temp.setCounter(temp.getCounter() + 1);
      repository.saveAndFlush(temp);
      return CompletableFuture.completedFuture(
          calculateAvg(temp.getCounter(), temp.getSum(), newEntry));
  }


}
