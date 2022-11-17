package com.weather.application.configuration;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.UniformRandomBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class BeanConfigs {

  @Bean
  public RetryTemplate createRetryTemplate() {
    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
    retryPolicy.setMaxAttempts(10);

    UniformRandomBackOffPolicy backOffPolicy = new UniformRandomBackOffPolicy();
    backOffPolicy.setMinBackOffPeriod(1000);
    backOffPolicy.setMaxBackOffPeriod(10000);

    RetryTemplate retryTemplate = new RetryTemplate();
    retryTemplate.setRetryPolicy(retryPolicy);
    retryTemplate.setBackOffPolicy(backOffPolicy);
    return retryTemplate;
  }

  @Bean
  public Executor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);
    executor.setMaxPoolSize(2);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("WeatherAsyncTransactions");
    executor.initialize();
    return executor;
  }


}
