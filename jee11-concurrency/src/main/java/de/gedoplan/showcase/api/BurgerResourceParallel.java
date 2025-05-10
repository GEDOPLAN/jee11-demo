package de.gedoplan.showcase.api;

import de.gedoplan.showcase.domain.Bun;
import de.gedoplan.showcase.domain.DoughType;
import de.gedoplan.showcase.domain.Patty;
import de.gedoplan.showcase.domain.PattyType;
import de.gedoplan.showcase.service.concurrency.UseVirtualIfSupported;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import jakarta.annotation.Resource;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("par/burger")
public class BurgerResourceParallel extends AbstractBurgerResource {

  // @Resource(lookup = "java:comp/UseVirtualIfSupportedExecutor")
  @Inject @UseVirtualIfSupported
  ManagedExecutorService executor;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
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


}
