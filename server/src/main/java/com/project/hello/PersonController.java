package com.project.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Adas on 2017-03-28.
 */
@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("")
    public PersonDTO get(){
        Person person = personRepository.findByName("Greg");
        return new PersonDTO(person);
    }
}
