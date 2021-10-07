package backend.restapp.mappers.Impl;

import backend.restapp.dto.PersonDto;
import backend.restapp.mappers.PersonMapper;
import backend.restapp.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonMapperImpl implements PersonMapper {

    @Override
    public Person toPerson(PersonDto personDto) {
        Person person = new Person();
        person.setId(personDto.getId());
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setEmail(personDto.getEmail());
        person.setPassword(personDto.getPassword());
        return person;
    }

    @Override
    public PersonDto toPersonDto(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setEmail(person.getEmail());
        personDto.setPassword(person.getPassword());
        return personDto;
    }

    @Override
    public List<PersonDto> toPersonDtos(Set<Person> personList) {
        return personList.stream()
                .map(person -> toPersonDto(person))
                .collect(Collectors.toList());
    }
}
