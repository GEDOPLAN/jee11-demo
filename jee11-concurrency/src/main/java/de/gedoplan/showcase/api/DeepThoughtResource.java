package de.gedoplan.showcase.api;

import de.gedoplan.showcase.service.DeepThoughService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;

@Path("deep-thought")
@ApplicationScoped
public class DeepThoughtResource {

  @Inject
  DeepThoughService deepThoughService;

  @Inject
  Log log;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getAnswer() {
    CompletableFuture<String> answer = deepThoughService.getAnswerToQuestionAboutLifeUniverseAndEverything();
    while (true) {
      try {
        if (answer.isDone()) {
          return answer.get();
        }

        log.debug("Deep Thought is still thinking - doing something else in between");
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // ignore
      } catch (ExecutionException e) {
        return "System error: " + e;
      }
    }
  }
}
