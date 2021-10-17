package backend.restapp.repo;

import backend.restapp.model.Tools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ToolsRepo extends JpaRepository<Tools, Long> {
    @Query("select t from Tools t where t.id is not null")
    List<Tools> selectAll();

    @Query("select count(t) from Tools t where t.toolName like %:toolName%")
    long countHasName(@Param("toolName") String toolName);

    @Query("select t from Tools t where upper(t.toolName) like upper(concat('%', ?1, '%'))")
    Optional<Tools> dsf(String toolName);

}
