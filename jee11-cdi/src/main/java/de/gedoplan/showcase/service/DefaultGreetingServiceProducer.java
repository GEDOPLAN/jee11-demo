package de.gedoplan.showcase.service;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.interceptor.Interceptor;

@ApplicationScoped
public class DefaultGreetingServiceProducer {
  @Produces GreetingService produce() {
    return new GreetingService() {
      @Override
      public String sayHello() {
        return "Hello, world!";
      }
    };
  }
}
