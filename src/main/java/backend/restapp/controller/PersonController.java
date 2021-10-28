package backend.restapp.controller;

import backend.restapp.dto.PersonDto;
import backend.restapp.mappers.PersonMapper;
import backend.restapp.service.impl.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;



    public PersonController(PersonService personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @GetMapping
    public List<PersonDto> getAllPerson(@RequestParam(required = false) String query){
        return personMapper.toPersonDtos(personService.findAllPerson(query));
    }
}
