package backend.restapp.service.impl;

import backend.restapp.model.Person;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

public interface PersonService {
    Set<Person> findAllPerson(String query);
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
    UserDetails loadUserById(String id);
}
