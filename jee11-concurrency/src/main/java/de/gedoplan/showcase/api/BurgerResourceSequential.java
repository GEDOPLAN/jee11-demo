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

import java.util.List;

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
@Path("seq/burger")
public class BurgerResourceSequential extends AbstractBurgerResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> getBurger(@QueryParam("bun") @DefaultValue("WHEAT") DoughType bunType, @QueryParam("patty") @DefaultValue("BEEF") PattyType pattyType) {

    this.logger.debug("----- Start burger production ---------");

    Bun bun = bakeBun(supplyBunDough(bunType));

    Patty patty = pattyType.isVeggie()
      ? this.miseEnPlaceService.getVegetarianPatty(pattyType)
      : supplyPattyMeat(pattyType.toString());
    patty = fryPattie(patty);

    List<String> parts = List.of(
      bun.getUpperHalf(),
      this.miseEnPlaceService.getSauce(),
      this.miseEnPlaceService.getTomato(),
      this.miseEnPlaceService.getCheese(),
      patty.toString(),
      this.miseEnPlaceService.getSalad(),
      bun.getLowerHalf()
    );

    this.logger.debug("----- Deliver burger ------------------");
    return parts;
  }
}
