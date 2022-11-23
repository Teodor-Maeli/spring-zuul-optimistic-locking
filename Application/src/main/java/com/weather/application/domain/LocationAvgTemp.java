package com.weather.application.domain;

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
  @Column(name = "location", unique = true)
  private String location;
  @Column(name = "sum")
  private Double sum;

  @Column(name = "counter")
  private Integer counter;


  public LocationAvgTemp() {
    this.sum = 0.0;
    this.counter = 0;
  }


  public LocationAvgTemp(String location ,Double sum) {
    this.location = location;
    this.sum = sum;
    this.counter = 1;
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

  public Integer getCounter() {
    return counter;
  }

  public void setCounter(Integer counter) {
    this.counter = counter;
  }
}
