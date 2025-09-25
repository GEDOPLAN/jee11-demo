package de.gedoplan.showcase.persistence;

import java.util.Optional;

import de.gedoplan.showcase.entity.Publisher;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;


@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Integer> {
  @Query("select count(x) from Publisher x")
  long count();
}
