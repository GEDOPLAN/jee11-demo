package de.gedoplan.showcase.jee11.jp.rest;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

import de.gedoplan.showcase.jee11.jp.model.Person;
import de.gedoplan.showcase.jee11.jp.model.Status;
import de.gedoplan.showcase.jee11.jp.repository.PersonRepository;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;

@Log
@Path("person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {
  // Injektion der UriInfo erfolgt nun über @Inject statt @Context
  @Inject
  UriInfo uriInfo;

  @Inject
  PersonRepository personRepository;

  @GET
  public List<Person> getPersons(@DefaultValue("default") @QueryParam("type") String type) {
    return switch (type) {
      case "short" -> personRepository.findAllShortSyntax();
      case "safe" -> personRepository.findAllTypeSafe();
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
  public Response addPerson(Person person) {
    person.setStatus(Status.NEW);
    try {
      personRepository.save(person);
    } catch (ConstraintViolationException exception) {
      return createResponse(Response.status(Response.Status.BAD_REQUEST), exception.getConstraintViolations());
    }
    return Response.created(
            uriInfo
                .getAbsolutePathBuilder()
                .path(person.getId().toString())
                .build())
        .build();
  }

  private Response createResponse(Response.ResponseBuilder responseBuilder, Set<ConstraintViolation<?>> violations) {
    BiConsumer<String, String> addHeader = responseBuilder::header;
    violations.forEach(violation -> addHeader.accept(violation.getPropertyPath().toString(), violation.getMessage()));
    return responseBuilder.build();
  }
}
