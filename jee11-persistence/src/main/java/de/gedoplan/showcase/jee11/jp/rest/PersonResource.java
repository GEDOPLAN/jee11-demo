package de.gedoplan.showcase.jee11.jp.rest;

import de.gedoplan.showcase.jee11.jp.model.Person;
import de.gedoplan.showcase.jee11.jp.model.Status;
import de.gedoplan.showcase.jee11.jp.repository.PersonRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

@Log
@Path("person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {
  private static final String ERROR_HEADER = "Error-Message";

  // Injektion der UriInfo erfolgt nun über @Inject statt @Context
  @Inject
  UriInfo uriInfo;

  @Inject
  PersonRepository personRepository;

  @GET
  public List<Person> getPersons(@DefaultValue("default") @QueryParam("type") String type) {
    return switch (type) {
      case "short" -> personRepository.findAllShortSyntax();
      case "save" -> personRepository.findAllTypeSave();
      case "default" -> personRepository.findAll();
      default -> Collections.emptyList();
    };
  }

  @GET
  @Path("{name}")
  public Person getPerson(@PathParam("name") String name) {
    return personRepository.findByName(name);
  }

  @GET
  @Path("{name}/reg")
  public Integer getYearOfRegistration(@PathParam("name") String name) {
    return personRepository.getRegistrationYear(name);
  }

  @POST
  @Transactional
  public Response addPerson(Person person) {
    person.setStatus(Status.NEW);
    try {
      personRepository.save(person);
    } catch (Exception exception) {
      // Auf Fehler bei der Jakarta Validation reagieren
      if (exception instanceof ConstraintViolationException) {
        return createResponse(Response.status(Response.Status.BAD_REQUEST), (ConstraintViolationException) exception);
      }
      // Auf Fehler bei den SQL-Checks reagieren
      if (exception instanceof org.hibernate.exception.ConstraintViolationException) {
        return Response.status(Response.Status.BAD_REQUEST).header(ERROR_HEADER, ((org.hibernate.exception.ConstraintViolationException) exception).getConstraintName()).build();
      }
      return Response.status(Response.Status.BAD_REQUEST).header(ERROR_HEADER, exception.getLocalizedMessage()).build();
    }
    return Response.created(
            uriInfo
                .getAbsolutePathBuilder()
                .path(person.getId().toString())
                .build())
        .build();
  }

  private Response createResponse(Response.ResponseBuilder responseBuilder, ConstraintViolationException exception) {
    BiConsumer<String, String> addHeader = responseBuilder::header;
    exception.getConstraintViolations().forEach(violation -> addHeader.accept(ERROR_HEADER, violation.getPropertyPath().toString() + " - " +  violation.getMessage()));
    return responseBuilder.build();
  }
}
