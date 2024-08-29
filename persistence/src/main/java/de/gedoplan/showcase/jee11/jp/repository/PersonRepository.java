package de.gedoplan.showcase.jee11.jp.repository;

import de.gedoplan.showcase.jee11.jp.model.Person;
import de.gedoplan.showcase.jee11.jp.model.Person_;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PersonRepository {
  @Inject
  EntityManager entityManager;

  public List<Person> findAll() {
    return entityManager.createQuery("SELECT p FROM Person p", Person.class).getResultList();
//    return entityManager.createQuery("FROM Person", Person.class).getResultList();
//    return entityManager.createNamedQuery(Person_.QUERY_PERSON_FIND_ALL, Person.class).getResultList();
  }

  @Transactional
  public void save(Person person) {
    entityManager.persist(person);
  }
}
