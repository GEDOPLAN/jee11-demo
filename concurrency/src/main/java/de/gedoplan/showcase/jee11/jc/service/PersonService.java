package de.gedoplan.showcase.jee11.jc.service;

import jakarta.annotation.Resource;
import jakarta.enterprise.concurrent.*;
import jakarta.enterprise.context.RequestScoped;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;

@RequestScoped
public class PersonService {
  @Resource(lookup = "java:jboss/ee/concurrency/factory/default")
  ThreadFactory threadFactory;

  public List<String> getListOfPersons() {
//    try (var executor = Executors.newThreadPerTaskExecutor(threadFactory)) {
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      IntStream.range(1, 10000).forEach(value -> {
        executor.submit(() -> getPerson(value));
      });
    }

    return Collections.emptyList();
  }

  CompletableFuture<String> getPerson(int i) throws InterruptedException {
    System.out.println(Thread.currentThread().getName() + " - " + Thread.currentThread().isVirtual());
    var result = "Person " + i;
    Thread.sleep(100);
    return Asynchronous.Result.complete(result);
  }
}
