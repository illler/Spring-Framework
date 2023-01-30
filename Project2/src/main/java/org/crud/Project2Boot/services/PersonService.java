package org.crud.Project2Boot.services;

import org.crud.Project2Boot.models.Person;
import org.crud.Project2Boot.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
