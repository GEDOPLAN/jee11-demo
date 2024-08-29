package de.gedoplan.showcase.jee11.jp;

import de.gedoplan.showcase.jee11.jp.model.Address;
import de.gedoplan.showcase.jee11.jp.model.Person;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.*;

@ApplicationScoped
public class EntityManagerProducer {
  private EntityManagerFactory emf;

  @PostConstruct
  void init() {
    emf = new PersistenceConfiguration("jee11-pu")
        .jtaDataSource("java:/jdbc/jee11-demo")
        .managedClass(Address.class)
        .managedClass(Person.class)
        .transactionType(PersistenceUnitTransactionType.JTA)
        .validationMode(ValidationMode.CALLBACK)
        .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION, "drop-and-create")
        .createEntityManagerFactory();
  }

  @Produces
  EntityManager createEntityManager() {
    return emf.createEntityManager();
  }
}
