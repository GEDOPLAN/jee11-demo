package de.gedoplan.showcase.jee11.jp;

import de.gedoplan.showcase.jee11.jp.model.Address;
import de.gedoplan.showcase.jee11.jp.model.Person;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import jakarta.persistence.PersistenceUnitTransactionType;
import jakarta.persistence.ValidationMode;

@ApplicationScoped
public class EntityManagerProducer {
  private EntityManagerFactory emf;

  @PostConstruct
  void init() {
    emf = new PersistenceConfiguration("showcase-pu")
        .jtaDataSource("java:/jdbc/showcase")
        .managedClass(Person.class)
        .managedClass(Address.class)
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
