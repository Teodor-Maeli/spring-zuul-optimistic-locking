package com.weather.application.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryAspect {

  private RetryTemplate retryTemplate;

  public RetryAspect(RetryTemplate retryTemplate) {
    this.retryTemplate = retryTemplate;
  }

  @Around("execution(* com.weather.application.rest..*(..))")
  public Object retry(final ProceedingJoinPoint point) throws Throwable {
    return retryTemplate.execute(retryCallBack -> point.proceed());
  }






}
