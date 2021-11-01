package backend.restapp.service;

import backend.restapp.exeptions.ResourceNotFoundException;
import backend.restapp.model.Person;
import backend.restapp.repo.PersonRepo;
import backend.restapp.service.impl.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class PersonServiceImp implements PersonService {

    private final PersonRepo personRepo;

    public PersonServiceImp(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public Set<Person> findAllPerson(String query) {
        Set<Person> personSet = null;
        try {
            personSet = new HashSet<>();
            if (ObjectUtils.isEmpty(query)) {
                personSet.addAll(personRepo.findAll());
                if (personSet.isEmpty()) {
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else
                    new ResponseEntity<>(HttpStatus.OK);
            }

        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return personSet;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User by Email not found" + email));
        log.info("Email not found: {}", email);
        return UserDetailsImpl.build(person);
    }

    @Override
    public UserDetails loadUserById(String id) {
        Person person = personRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        log.info("User by id: {}", id);
        return UserDetailsImpl.build(person);
    }
}


