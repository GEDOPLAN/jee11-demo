package de.gedoplan.showcase.api;

import de.gedoplan.showcase.domain.Bun;
import de.gedoplan.showcase.domain.Dough;
import de.gedoplan.showcase.domain.DoughType;
import de.gedoplan.showcase.domain.Patty;
import de.gedoplan.showcase.domain.PattyType;
import de.gedoplan.showcase.service.DoughService;
import de.gedoplan.showcase.service.MeatService;
import de.gedoplan.showcase.service.MiseEnPlaceService;
import de.gedoplan.showcase.service.OvenService;
import de.gedoplan.showcase.service.StoveService;
import de.gedoplan.showcase.util.ThreadUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import jakarta.annotation.Resource;
import jakarta.enterprise.concurrent.ManagedExecutorDefinition;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Path("vt/burger")
@ManagedExecutorDefinition(name = "java:comp/Executor", virtual = true)
public class BurgerResourceVirtualThread {

  @Inject
  @RestClient
  DoughService doughService;

  @Inject
  @RestClient
  OvenService ovenService;

  @Inject
  @RestClient
  MeatService meatService;

  @Inject
  @RestClient
  StoveService stoveService;

  @Inject
  MiseEnPlaceService miseEnPlaceService;

  @Resource(lookup = "java:comp/Executor")
  ManagedExecutorService executor;

  @Inject
  Log logger;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  //  @RunOnVirtualThread
  public List<String> getBurger(@QueryParam("bun") @DefaultValue("WHEAT") DoughType bunType, @QueryParam("patty") @DefaultValue("BEEF") PattyType pattyType)
    throws ExecutionException, InterruptedException {

    this.logger.debug("----- Start burger production ---------");

    Future<Bun> bunFuture = executor.submit(() -> bakeBun(supplyBunDough(bunType)));

    Future<Patty> pattieFuture = executor.submit(() -> {
      Patty patty = pattyType.isVeggie() ? this.miseEnPlaceService.getVegetarianPatty(pattyType) : supplyPattyMeat(pattyType.toString());
      return fryPattie(patty);
    });

    List<String> parts = List.of(
      bunFuture.get().getUpperHalf(),
      this.miseEnPlaceService.getSauce(),
      this.miseEnPlaceService.getTomato(),
      this.miseEnPlaceService.getCheese(),
      pattieFuture.get().toString(),
      this.miseEnPlaceService.getSalad(),
      bunFuture.get().getLowerHalf());

    this.logger.debug("----- Deliver burger ------------------");
    return parts;
  }

  private Dough supplyBunDough(DoughType bunType) {
    log("Get dough (" + bunType + ")");
    return this.doughService.supplyBunDough(bunType, 50);
  }

  private Bun bakeBun(Dough dough) {
    log("Bake bun (" + dough.getType() + ")");
    return this.ovenService.bakeBun(dough);
  }

  private Patty supplyPattyMeat(String meatType) {
    log("Get patty (" + meatType + ")");
    return this.meatService.supplyPattyMeat(meatType, 200);
  }

  private Patty fryPattie(Patty patty) {
    log("Fry pattie (" + patty.getType() + ")");
    return this.stoveService.fryPattie(patty);
  }

  private void log(String message) {
    this.logger.debug(message + " on " + (ThreadUtil.isVirtualThread() ? "virtual" : "platform") + " thread");
  }

}
