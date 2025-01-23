package de.gedoplan.showcase.service;

import java.time.ZoneId;
import java.util.concurrent.ScheduledFuture;

import jakarta.annotation.Resource;
import jakarta.enterprise.concurrent.CronTrigger;
import jakarta.enterprise.concurrent.ManagedScheduledExecutorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Shutdown;
import jakarta.enterprise.event.Startup;
import jakarta.inject.Inject;

import org.jboss.logging.Logger;

@ApplicationScoped
public class TickerService {

  @Resource
  ManagedScheduledExecutorService executorService;

  @Inject
  Logger log;

  private ScheduledFuture<?> ticker;

  public void tick() {
    log.debug("Tick!");
  }

  void startTicker(@Observes Startup event) {
    log.debug("Starting ticker");
    ticker = executorService.schedule(this::tick, new CronTrigger("0 * * * * *", ZoneId.of("Europe/Berlin")));
  }

  void stopTicker(@Observes Shutdown event) {
    if (ticker != null && !ticker.isCancelled()) {
      log.debug("Stopping ticker");
      ticker.cancel(true);
    }
  }

}
