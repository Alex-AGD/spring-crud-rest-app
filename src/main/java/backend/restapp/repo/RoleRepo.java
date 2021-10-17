package backend.restapp.repo;

import backend.restapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    //Optional<Role> findByName(ERole name);
}
