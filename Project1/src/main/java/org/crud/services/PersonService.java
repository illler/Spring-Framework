package org.crud.services;


import org.crud.models.Person;
import org.crud.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public List<Person> findAll(){
        return personRepository.findAll();
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person){
        person.setPerson_id(id);
        personRepository.save(person);
    }



    @Transactional
    public Person show(int id){
        Optional<Person> optionalPerson =  personRepository.findById(id);
        return optionalPerson.orElse(null);
    }

    @Transactional
    public boolean check(int id){
        Optional<Person> optionalPerson = personRepository.findById(id);
        return !optionalPerson.get().getItems().isEmpty();
    }

    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }
}
