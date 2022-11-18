package com.weather.application.aspects;

import java.util.Random;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
public class RetryAspect {

  public static final Logger log = LoggerFactory.getLogger(RetryAspect.class);

  @Pointcut("@annotation(com.weather.application.aspects.RetryOnFailure) && execution(* *(..))")
  public void retryableMethod() {
  }

  @Around("retryableMethod()")
  public Object retryMethodAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    Object response = null;
    boolean success = false;
    int retries = ((MethodSignature) joinPoint.getSignature()).getMethod()
        .getAnnotation(RetryOnFailure.class).retries();
    int max = ((MethodSignature) joinPoint.getSignature()).getMethod()
        .getAnnotation(RetryOnFailure.class).maxRetryDelay();
    int min = ((MethodSignature) joinPoint.getSignature()).getMethod()
        .getAnnotation(RetryOnFailure.class).minDelayAttempt();
    int counter = 0;
    while (!success) {
      log.info("counter: {}", counter);
      log.info("log {}" ,joinPoint.getKind());
      try {
        response = joinPoint.proceed(joinPoint.getArgs());
        success = true;
      } catch (Exception e) {
        counter++;
        log.info("Retry No {} failed, exception catched: {}", counter, e.getClass());
        Thread.sleep(new Random().nextInt(max - min + 1) + min);
        if (counter >= retries) {
          log.info("Reached max retry attempts {}", e.getClass());
          throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Request did not proceed , try later!");
        }
        log.info("Reattempting to complete");
      }
    }
    return response;
  }


}
