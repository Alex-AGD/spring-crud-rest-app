package backend.restapp.config;

import backend.restapp.model.Person;
import backend.restapp.repo.PersonRepo;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;

@RestController

public class SessionController {

    @Bean
    public PrincipalExtractor principalExtractor(PersonRepo personRepo,HttpServletResponse response) {
        return map -> {
            String email = (String) map.get("email");
            Person person = personRepo.findByEmail(email).orElseGet(() -> {
                Person newPerson = new Person();
                newPerson.setUsername((String) map.get("name"));
                newPerson.setEmail((String) map.get("email"));
                newPerson.setGender((String) map.get("gender"));
                newPerson.setLocale((String) map.get("locale"));
                newPerson.setUserPicture((String) map.get("picture"));
                return newPerson;
            });
            person.setLastVisitDate(LocalDateTime.now());
            try {
                response.sendRedirect("http://localhost:8080");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return personRepo.save(person);
        };
    }

    @GetMapping("sessions/me")
  public ResponseEntity<?> user(Principal principal) {
    if (principal == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "unauthorized"));
    }
    return ResponseEntity.ok(principal);
  }

  @GetMapping("sessions/github/callback")
  public void githubCallback(HttpServletResponse response) throws IOException {
    response.sendRedirect("http://localhost:8081/login");
  }

}
