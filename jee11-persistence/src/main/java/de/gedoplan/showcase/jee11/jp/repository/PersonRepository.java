package de.gedoplan.showcase.jee11.jp.repository;

import java.util.List;

import de.gedoplan.showcase.jee11.jp.model.Person;
import de.gedoplan.showcase.jee11.jp.model.Person_;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PersonRepository {
  @Inject
  EntityManager entityManager;

  public List<Person> findAll() {
    return entityManager.createQuery("SELECT p FROM Person p", Person.class).getResultList();
  }

  public List<Person> findAllShortSyntax() {
    return entityManager.createQuery("FROM Person", Person.class).getResultList();
  }

  public List<Person> findAllTypeSafe() {
    return entityManager.createNamedQuery(Person_.QUERY_PERSON_FIND_ALL, Person.class).getResultList();
  }

  public Integer getRegistrationYear(String name) {
    return entityManager
        .createQuery("SELECT cast(left(p.number,4) as Integer) FROM Person p WHERE p.name = :name", Integer.class)
        .setParameter("name", name)
        .getSingleResultOrNull();
  }

  public Person findByName(String name) {
    return entityManager
        .createQuery("SELECT p FROM Person p WHERE p.name = :name", Person.class)
        .setParameter("name", name)
        .getSingleResultOrNull();
  }

  public long count() {
    return entityManager
    .createQuery("select count(p) from Person p", Long.class)
    .getSingleResult();
  }

  @Transactional
  public void save(Person person) {
    entityManager.persist(person);
  }
}
