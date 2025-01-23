package de.gedoplan.showcase.api;

import de.gedoplan.showcase.service.SeminarService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import org.jboss.logging.Logger;

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
