package de.gedoplan.showcase.api;

import de.gedoplan.showcase.service.DeepThoughService;
import de.gedoplan.showcase.service.TickerService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;

@Path("ticker")
@ApplicationScoped
public class TickerResource {

  @Inject
  TickerService tickerService;

  @Inject
  Log log;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getAnswer() {
     tickerService.tick();

     return "Ticker started";
  }
}
