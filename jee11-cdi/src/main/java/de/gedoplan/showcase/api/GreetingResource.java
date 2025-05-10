package de.gedoplan.showcase.api;

import de.gedoplan.showcase.service.GreetingService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("greeting")
public class GreetingResource {

  @Inject
  GreetingService greetingService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getHello() {
    return greetingService.sayHello();
  }
}
