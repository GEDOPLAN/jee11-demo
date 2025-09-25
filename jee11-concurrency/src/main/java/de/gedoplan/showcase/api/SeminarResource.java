package de.gedoplan.showcase.api;

import org.jboss.logging.Logger;

import de.gedoplan.showcase.service.SeminarService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("seminar")
@ApplicationScoped
public class SeminarResource {

  @Inject
  SeminarService seminarService;

  @Inject
  Logger logger;

  @POST
  @Consumes("*/*")
  public void enrol(String name) {
    if (name == null) {
      name = "Hugo";
    }
    seminarService.attend(name);
  }
}
