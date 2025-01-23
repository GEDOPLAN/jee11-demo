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

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

public abstract class AbstractBurgerResource {

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

  @Inject
  Logger logger;

  @Produces(MediaType.APPLICATION_JSON)
  public abstract List<String> getBurger(@QueryParam("bun") @DefaultValue("WHEAT") DoughType bunType, @QueryParam("patty") @DefaultValue("BEEF") PattyType pattyType) throws Exception;

  protected Dough supplyBunDough(DoughType doughType) {
    this.logger.debugf("Get dough (%s) on %s thread", doughType, ThreadUtil.getKindOfThread());
    return this.doughService.supplyBunDough(doughType, 50);
  }

  protected Bun bakeBun(Dough dough) {
    this.logger.debugf("Bake bun (%s) on %s thread", dough.getType(), ThreadUtil.getKindOfThread());
    return this.ovenService.bakeBun(dough);
  }

  protected Patty supplyPattyMeat(String meatType) {
    this.logger.debugf("Get patty (%s) on %s thread", meatType, ThreadUtil.getKindOfThread());
    return this.meatService.supplyPattyMeat(meatType, 200);
  }

  protected Patty fryPattie(Patty patty) {
    this.logger.debugf("Fry patty (%s) on %s thread", patty.getType(), ThreadUtil.getKindOfThread());
    return this.stoveService.fryPattie(patty);
  }
}
