package de.gedoplan.showcase.jee11.jp.service;

import java.time.LocalDate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.gedoplan.showcase.jee11.jp.model.Address;
import de.gedoplan.showcase.jee11.jp.model.Person;
import de.gedoplan.showcase.jee11.jp.model.Status;
import de.gedoplan.showcase.jee11.jp.repository.PersonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class InitPersonDemoDataService {
  @Inject
  PersonRepository personRepository;

  private static final Log LOG = LogFactory.getLog(InitPersonDemoDataService.class);

  private static final Person DAGOBERT = Person.builder()
      .name("Dagobert Duck")
      .status(Status.ACTIVE)
      .number("2025.42")
      .birthday(LocalDate.of(1912, 10, 18))
      .address(new Address("Money Bin, Killmotor Hill", "Duckburg, Calisota", "12345"))
      .build();

      private static final Person DONALD = Person.builder()
      .name("Donald Duck")
      .status(Status.ACTIVE)
      .number("2025.43")
      .birthday(LocalDate.of(1934, 6, 9))
      .address(new Address("1313 Webfoot Walk", "Duckburg, Calisota", "12345"))
      .build();

  /**
   * Create test/demo data.
   * Attn: Interceptors may not be called, if method is private!
   *
   * @param event
   *          Application scope initialization event
   */
  @Transactional
  void createDemoData(@Observes Startup event) {
    try {
      if (this.personRepository.count() == 0) {
        this.personRepository.save(DAGOBERT);
        this.personRepository.save(DONALD);
        LOG.debug("Created demo data");
      }
    } catch (Exception e) {
      LOG.warn("Cannot create demo data", e);
    }

  }

}
