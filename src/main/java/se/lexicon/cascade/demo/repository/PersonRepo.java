package se.lexicon.cascade.demo.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.cascade.demo.entity.Person;

public interface PersonRepo extends CrudRepository<Person, Long> {
}
