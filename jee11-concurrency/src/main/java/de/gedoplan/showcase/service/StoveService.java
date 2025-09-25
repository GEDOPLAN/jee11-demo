package de.gedoplan.showcase.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import de.gedoplan.showcase.domain.Patty;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "StoveService")
@Path("stove")
public interface StoveService {
  @POST
  @Path("patty")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Patty fryPattie(Patty patty);

}
