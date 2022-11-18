package com.weather.application.rest;
import com.weather.application.aspects.RetryOnFailure;
import com.weather.application.dto.LocationAvgTempDto;
import com.weather.application.service.LocationAvgTempService;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
  @RetryOnFailure(retries = 20,maxDelayAttempt = 2000)
  public ResponseEntity<Double> updateLocation(@PathVariable String location, @PathVariable double temperature) {
    LocationAvgTempDto dto = new LocationAvgTempDto(location,temperature);
    return ResponseEntity.ok(service.updateAverageTemperature(dto));
  }


  //for testing purpose only
  @PostMapping("/start/threads")
  @RetryOnFailure(retries = 20,maxDelayAttempt = 2000)
    public ResponseEntity<List<CompletableFuture<Double>>> start() throws InterruptedException {
      CompletableFuture<Double> temp = service.test("VAR", 33.3);
      CompletableFuture<Double> temp1 = service.test1("VAR", 33.3);
      CompletableFuture.allOf(temp, temp1).join();
      return ResponseEntity.ok(Arrays.asList(temp,temp1));
    }

}
