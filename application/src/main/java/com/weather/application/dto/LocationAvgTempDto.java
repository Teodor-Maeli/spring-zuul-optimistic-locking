package com.weather.application.dto;

public class LocationAvgTempDto {

  private String location;
  private Double entry;


  public LocationAvgTempDto() {
  }

  public LocationAvgTempDto(String location, Double entry) {
    this.location = location;
    this.entry = entry;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Double getEntry() {
    return entry;
  }

  public void getEntry(Double averageTemperature) {
    this.entry = averageTemperature;
  }
}
