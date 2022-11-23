package com.weather.application.service.impl;

import com.weather.application.domain.LocationAvgTemp;
import com.weather.application.dto.LocationAvgTempDto;
import com.weather.application.repository.LocationAvgTempRepository;
import com.weather.application.service.LocationAvgTempService;
import java.util.function.BiFunction;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LocationAvgTempServiceImpl implements LocationAvgTempService {

  private final LocationAvgTempRepository repository;

  public LocationAvgTempServiceImpl(LocationAvgTempRepository repository) {
    this.repository = repository;
  }

  private final BiFunction<Integer, Double, Double> CALC_AVG = (x, y) -> y / x;

  @Override
  @Transactional
  public Double updateAverageTemperature(LocationAvgTempDto newEntry) {
    LocationAvgTemp entity = repository.findByLocation(newEntry.getLocation())
        .orElse(new LocationAvgTemp());
    entity.setSum(entity.getSum() + newEntry.getEntry());
    entity.setLocation(newEntry.getLocation());
    repository.save(entity);
    return CALC_AVG.apply(entity.getCounter(), entity.getSum());
  }

}
