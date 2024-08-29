package de.gedoplan.showcase.jee11.jp.rest;

import de.gedoplan.showcase.jee11.jp.model.Person;
import de.gedoplan.showcase.jee11.jp.model.Status;
import de.gedoplan.showcase.jee11.jp.repository.PersonRepository;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

@Log
@Path("person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {
  @Inject
  UriInfo uriInfo;

  @Inject
  Validator validator;

  @Inject
  PersonRepository personRepository;

  @GET
  public List<Person> getPersons() {
    return personRepository.findAll();
  }

  /**
   * Using Jakarta RESTful Web Servic to check Person for Constraint Violations
   */
  @POST
  @Path("check")
  public Response checkPerson(@Valid Person person) {
    log.info("Check Person: " + person);
    return Response.ok().build();
  }

  /**
   * Using Jakarta Validation to check Person for Constraint Violations
   */
  @POST
  @Path("validate")
  public Response validatePerson(Person person) {
    log.info("Validate Person: " + person);
    var violations = validator.validate(person);
    if (!violations.isEmpty()) {
      return createResponse(Response.status(Response.Status.BAD_REQUEST), violations);
    }
    return Response.ok().build();
  }

  @POST
  public Response addPerson(Person person) {
    person.setStatus(Status.NEW);
    try {
      personRepository.save(person);
    } catch (ConstraintViolationException exception) {
      return Response.status(Response.Status.BAD_REQUEST).header("persistence-validation-exception", exception.getLocalizedMessage()).build();
    }
    return Response.created(
            uriInfo
                .getAbsolutePathBuilder()
                .path(person.getId().toString())
                .build())
        .build();
  }

  private Response createResponse(Response.ResponseBuilder responseBuilder, Set<ConstraintViolation<Person>> violations) {
    BiConsumer<String, String> addHeader = responseBuilder::header;
    violations.forEach(violation -> addHeader.accept(violation.getPropertyPath().toString(), violation.getMessage()));
    return responseBuilder.build();
  }
}
