package com.weather.application.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.annotations.DynamicUpdate;

@Entity(name = "location_avg_temp")
@Table(name = "location_avg_temp")
@DynamicUpdate
public class LocationAvgTemp {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "location")
  private String location;
  @Column(name = "sum")
  private Double sum;
  @Column(name = "counter")
  private int counter;

  @Version
  @Column(name = "Version")
  private Integer version;

  public LocationAvgTemp() {
  }



  public LocationAvgTemp(String location, double sum, int counter) {
    this.location = location;
    this.sum = sum;
    this.counter = counter;
  }

  public Integer getVersion() {
    return version;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getSum() {
    return sum;
  }

  public void setSum(Double sum) {
    this.sum = sum;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public int getCounter() {
    return counter;
  }

  public void setCounter(int counter) {
    this.counter = counter;
  }

}