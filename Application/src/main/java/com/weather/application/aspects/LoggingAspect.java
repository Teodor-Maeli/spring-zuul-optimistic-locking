package com.weather.application.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  Logger log = LoggerFactory.getLogger(LoggingAspect.class);


  @Before("execution(* com.weather.application.service.LocationAvgTempService..*(..))")
  public void beforeLogger() {
    log.info("Update attempting..");
  }


  @Pointcut("execution(* com.weather.application.service.LocationAvgTempService..*(..))")
  public void afterReturningPointcut() {
  }

  @AfterReturning(pointcut = "afterReturningPointcut()", returning = "retVal")
  public void afterLogger(Double retVal) {
    log.info("Update completed with value of..{} degrees", retVal);
  }


}
