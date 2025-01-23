package de.gedoplan.showcase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.concurrent.Asynchronous;
import jakarta.enterprise.concurrent.Schedule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.jboss.logging.Logger;

@ApplicationScoped
public class SeminarService {

  private static final String COURSE_NAME = "JEE Power Workshop";
  private static final List<String> CHAPTERS = List.of("RESTful Webservices", "Persistence and Validation", "CDI", "Config and Health");

  private ConcurrentHashMap<String, List<String>> topicsLearned = new ConcurrentHashMap<>();

  @Inject
  Logger logger;

  public void enrol(String name) {
    logger.debugf("Enrolling %s for course %s", name, COURSE_NAME);
    topicsLearned.put(name, new ArrayList<>());
    attend(name);
  }

  // Course runs daily at 9:00; we simulate the days in 5 secs each ;-)
  // @Asynchronous(runAt = @Schedule(cron = "0 9 * * *"))
  @Asynchronous(runAt = @Schedule(cron = "*/5 * * * * *"))
  public CompletableFuture<List<String>> attend(String name) {

    List<String> topicsSoFar = topicsLearned.computeIfAbsent(name, x -> new ArrayList<>());
    int topicCount = topicsSoFar.size();

    // If student has completed the course earlier, simulate a fresh enrolment
    if (topicCount >= CHAPTERS.size()) {
      topicsSoFar.clear();
      topicCount = 0;
    }

    String topicToday = CHAPTERS.get(topicCount);
    logger.debugf("%s attends %s, learning %s today", name, COURSE_NAME, topicToday);
    topicsSoFar.add(topicToday);
    ++topicCount;

    if (topicCount < CHAPTERS.size()) {
      return null;
    }

    logger.debugf("%s has completed course %s", name, COURSE_NAME);
    return Asynchronous.Result.complete(topicsSoFar);
  }
}
