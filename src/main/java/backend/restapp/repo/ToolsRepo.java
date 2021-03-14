package backend.restapp.repo;

import backend.restapp.model.Tools;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToolsRepo extends JpaRepository<Tools,Long> {
    List<Tools> findByToolName(String name);


}
