package com.weather.application.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectConfig {

  Logger log  = LoggerFactory.getLogger(AspectConfig.class);

  private RetryTemplate retryTemplate;

  public AspectConfig(RetryTemplate retryTemplate) {
    this.retryTemplate = retryTemplate;
  }

  @Around("execution(* com.weather.application.rest..*(..))")
  public Object retry(final ProceedingJoinPoint point) throws Throwable {

    return retryTemplate.execute(retryCallBack -> point.proceed());
  }

  @Around("execution(* com.weather.application.service.LocationAvgTempService.test(..))")
  public Object test(final ProceedingJoinPoint point) throws Throwable {

    return retryTemplate.execute(retryCallBack -> point.proceed());
  }

  @Before("execution(* com.weather.application.service.LocationAvgTempService..*(..))")
  public void beforeLogger(){
    log.info("Update attempting..");
  }


  @AfterReturning("execution(* com.weather.application.service.LocationAvgTempService..*(..))")
  public void afterLogger(){
    log.info("Update completed..");
  }
  
  
  






}
