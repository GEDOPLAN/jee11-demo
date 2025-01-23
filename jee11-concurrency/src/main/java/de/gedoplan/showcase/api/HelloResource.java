package de.gedoplan.showcase.api;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("hello")
public class HelloResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getHello() {
    return "Hello, Jakarta EE 11!";
  }
}
