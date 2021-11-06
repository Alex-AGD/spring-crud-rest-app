package backend.restapp.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;

@RestController
@CrossOrigin(origins = "http://localhost:8080" , maxAge = 3600)
public class SessionController {

    @GetMapping("sessions/me")
  public ResponseEntity<?> user(Principal principal) {
    if (principal == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "unauthorized"));
    }
    return ResponseEntity.ok(principal);
  }



}
