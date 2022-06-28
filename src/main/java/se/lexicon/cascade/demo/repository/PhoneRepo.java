package se.lexicon.cascade.demo.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.cascade.demo.entity.Phone;

public interface PhoneRepo extends CrudRepository<Phone, Long> {
}
