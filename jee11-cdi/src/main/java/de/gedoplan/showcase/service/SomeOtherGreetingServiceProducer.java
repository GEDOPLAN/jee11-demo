package de.gedoplan.showcase.service;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Produces;
import jakarta.interceptor.Interceptor;

@ApplicationScoped
public class SomeOtherGreetingServiceProducer {
  @Produces @Alternative @Priority(Interceptor.Priority.APPLICATION + 400) GreetingService produce() {
    return new GreetingService() {
      @Override
      public String sayHello() {
        return "Hi!";
      }
    };
  }
}
