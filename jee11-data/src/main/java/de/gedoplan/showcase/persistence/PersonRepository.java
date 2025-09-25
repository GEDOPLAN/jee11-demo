package de.gedoplan.showcase.persistence;

import java.util.Optional;
import java.util.stream.Stream;

import de.gedoplan.showcase.entity.Person;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;


@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
  @Query("select count(x) from Person x")
  long count();

  @Find
  Stream<Person> findByName(String name);
}
