package de.gedoplan.showcase.service;

import java.util.concurrent.CompletableFuture;

import jakarta.enterprise.concurrent.Asynchronous;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Demo-Bean für asynchrone Methoden.
 *
 * "Deep Thought" ist der Supercomputer in "Per Anhalter durch die Galaxis", der von einer außerirdischen Kultur
 * speziell dafür gebaut wurde, die Antwort auf die Frage aller Fragen, nämlich die
 * "nach dem Leben, dem Universum und allem" zu errechnen. Nach einer Rechenzeit von 7,5 Millionen Jahren erbringt er
 * dann die Antwort, nämlich "Zweiundvierzig".
 *
 * @author dw
 *
 */
@ApplicationScoped
public class DeepThoughService {

  @Asynchronous
  public CompletableFuture<String> getAnswerToQuestionAboutLifeUniverseAndEverything() {
    try {
      Thread.sleep(7500);
    } catch (InterruptedException e) {
      // ignore
    }

    return Asynchronous.Result.complete("Zweiundvierzig");
  }
}
