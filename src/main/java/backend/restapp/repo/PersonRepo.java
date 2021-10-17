package backend.restapp.repo;

import backend.restapp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends JpaRepository<Person, String> {
   /* Optional<Person> findByName(String userName);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);*/
}
