package com.weather.application.service.impl;
import com.weather.application.domain.LocationAvgTemp;
import com.weather.application.dto.LocationAvgTempDto;
import com.weather.application.repository.LocationAvgTempRepository;
import com.weather.application.service.LocationAvgTempService;
import javax.transaction.Transactional;
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
      return LocationAvgTempService.calculateAvg(entity.getCounter(), entity.getSum());
    }
    entity = new LocationAvgTemp(newEntry.getLocation(), newEntry.getEntry());
    repository.save(entity);
    return LocationAvgTempService.calculateAvg(entity.getCounter()-1, newEntry.getEntry());
  }


}
