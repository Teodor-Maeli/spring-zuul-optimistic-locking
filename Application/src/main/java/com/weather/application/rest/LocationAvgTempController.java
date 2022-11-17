package com.weather.application.rest;;
import com.weather.application.dto.LocationAvgTempDto;
import com.weather.application.service.LocationAvgTempService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationAvgTempController {

  private LocationAvgTempService service;

  public LocationAvgTempController(LocationAvgTempService service) {
    this.service = service;
  }

  @PostMapping("/{location}/{temperature}")
  public ResponseEntity<Double> updateLocation(@PathVariable String location, @PathVariable double temperature) {
    LocationAvgTempDto entity = new LocationAvgTempDto(location,temperature);
    return ResponseEntity.ok(service.updateAverageTemperature(entity));
  }

}
