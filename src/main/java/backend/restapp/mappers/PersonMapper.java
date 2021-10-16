package backend.restapp.mappers;

import backend.restapp.dto.PersonDto;
import backend.restapp.model.Person;

import java.util.List;
import java.util.Set;

public interface PersonMapper {
    Person toPerson(PersonDto personDto);
    PersonDto toPersonDto(Person person);

   List<PersonDto> toPersonDtos(Set<Person> personList);
}
