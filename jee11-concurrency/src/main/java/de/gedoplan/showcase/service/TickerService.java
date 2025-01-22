package de.gedoplan.showcase.service;

import jakarta.enterprise.concurrent.Asynchronous;
import jakarta.enterprise.concurrent.Schedule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.commons.logging.Log;

@ApplicationScoped
public class TickerService {

  @Inject
  Log log;

  @Asynchronous(runAt = @Schedule(cron = "0 * * * * *"))
  public void tick() {
    log.debug("Tick!");
  }
}
