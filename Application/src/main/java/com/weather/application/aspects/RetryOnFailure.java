package com.weather.application.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryOnFailure {

  public int retries() default 5;
  public int minDelayAttempt() default 100;
  public int maxRetryDelay() default 1000;

}
