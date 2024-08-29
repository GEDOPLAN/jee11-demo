package de.gedoplan.showcase.jee11.jc.rest;

import de.gedoplan.showcase.jee11.jc.service.PersonService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {
  @Inject
  PersonService personService;

  @GET
  public List<String> getPersons() {
    return personService.getListOfPersons();
  }
}
