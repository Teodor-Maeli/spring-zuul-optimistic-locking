package com.weather.application.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  Logger log  = LoggerFactory.getLogger(LoggingAspect.class);



  @Before("execution(* com.weather.application.service.LocationAvgTempService..*(..))")
  public void beforeLogger(){
    log.info("Update attempting..");
  }


  @AfterReturning("execution(* com.weather.application.service.LocationAvgTempService..*(..))")
  public void afterLogger(){
    log.info("Update completed..");
  }
  
  
  


}
