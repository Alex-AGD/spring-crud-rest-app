package backend.restapp.controller;

import backend.restapp.model.ERole;
import backend.restapp.model.Person;
import backend.restapp.model.Role;
import backend.restapp.payload.request.LoginRequest;
import backend.restapp.payload.request.SignupRequest;
import backend.restapp.payload.response.JwtResponse;
import backend.restapp.payload.response.MessageResponse;
import backend.restapp.repo.PersonRepo;
import backend.restapp.repo.RoleRepo;
import backend.restapp.security.jwt.JwtUtils;
import backend.restapp.service.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final String NOT_FOUND = "Error: Role is not found";
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PersonRepo personRepo;

    @Autowired
    RoleRepo roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Bean
    public PrincipalExtractor principalExtractor(PersonRepo personRepo, HttpServletResponse response) {
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

            personRepo.save(person);
            try {
                response.sendRedirect("http://localhost:8080");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return person;
        };
    }

    @GetMapping("/google")
    public void githubCallback(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:8081/login");
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (Boolean.TRUE.equals(personRepo.existsByUsername(signUpRequest.getUsername()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (Boolean.TRUE.equals(personRepo.existsByEmail(signUpRequest.getEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        Person user = new Person(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(NOT_FOUND));
            log.info("Role not found: {}", userRole);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException(NOT_FOUND));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException(NOT_FOUND));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException(NOT_FOUND));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        personRepo.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
