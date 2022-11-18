package com.weather.application.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryOnFailure {

   int retries() default 5;
   int minDelayAttempt() default 100;
   int maxRetryDelay() default 1000;

}
