package backend.restapp.repo;

import backend.restapp.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepo extends JpaRepository<Messages, Long> {
}
