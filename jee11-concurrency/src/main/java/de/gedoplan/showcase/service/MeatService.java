package de.gedoplan.showcase.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import de.gedoplan.showcase.domain.Patty;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "MeatService")
@Path("meat")
public interface MeatService {
  @GET
  @Path("patty")
  @Produces(MediaType.APPLICATION_JSON)
  public Patty supplyPattyMeat(@QueryParam("type") String type, @QueryParam("weight") int weight);

}
