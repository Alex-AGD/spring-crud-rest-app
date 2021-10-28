package backend.restapp.service.impl;

import backend.restapp.model.Person;

import java.util.Set;

public interface PersonService {
    Set<Person> findAllPerson(String query);
}
