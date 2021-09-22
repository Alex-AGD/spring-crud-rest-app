package backend.restapp.repo;

import backend.restapp.model.Tools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ToolsRepo extends JpaRepository<Tools, Long> {
    @Query("select t from Tools t where t.id is not null")
    List<Tools> selectAll();

    @Query("select count(t) from Tools t where t.toolName like %:toolName%")
    long fgh(@Param("toolName") String toolName);

}
