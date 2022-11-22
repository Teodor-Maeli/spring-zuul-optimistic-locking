package com.weather.application.service;

import com.weather.application.dto.LocationAvgTempDto;;


public interface LocationAvgTempService {
  Double updateAverageTemperature(LocationAvgTempDto entity);

  static Double calculateAvg(int counter, Double sum) {
    Double avg = sum / (counter + 1);
    return avg;
  }
}
