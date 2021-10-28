package backend.restapp.repo;

import backend.restapp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String userName);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
