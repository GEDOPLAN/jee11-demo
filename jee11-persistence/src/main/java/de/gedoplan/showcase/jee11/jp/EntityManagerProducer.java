package de.gedoplan.showcase.jee11.jp;

import de.gedoplan.showcase.jee11.jp.model.Address;
import de.gedoplan.showcase.jee11.jp.model.Note;
import de.gedoplan.showcase.jee11.jp.model.Person;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.*;

@ApplicationScoped
public class EntityManagerProducer {
  private EntityManagerFactory emf;

  @PostConstruct
  void init() {
    emf = new PersistenceConfiguration("jee11-pu")
        .jtaDataSource("java:/jdbc/jee11-demo")
        .managedClass(Person.class)
        .managedClass(Address.class)
        .managedClass(Note.class)
        .transactionType(PersistenceUnitTransactionType.JTA)
        .validationMode(ValidationMode.CALLBACK)
        .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION, "drop-and-create")
        .createEntityManagerFactory();
  }

  // Since JEE 10
  void startup(@Observes Startup event) {
    //    emf.getSchemaManager().create(false);
  }

  @Produces
    //  @Priority(100)
  EntityManager createEntityManager() {
    return emf.createEntityManager();
  }
}
