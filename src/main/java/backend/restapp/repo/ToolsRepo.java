package backend.restapp.repo;

import backend.restapp.model.Tools;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ToolsRepo extends JpaRepository<Tools, Long> {
}
