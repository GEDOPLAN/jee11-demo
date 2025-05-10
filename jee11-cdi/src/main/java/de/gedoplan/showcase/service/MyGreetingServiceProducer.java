package de.gedoplan.showcase.service;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Produces;
import jakarta.interceptor.Interceptor;

@ApplicationScoped
public class MyGreetingServiceProducer {
  @Produces
  @Alternative
  @Priority(Interceptor.Priority.APPLICATION + 500)
  GreetingService produce() {
    return new GreetingService() {
      @Override
      public String sayHello() {
        return "Hello, Jakarta EE 11!";
      }
    };
  }
}
