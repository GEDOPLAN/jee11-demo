package de.gedoplan.showcase.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

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
