/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.service;

import co.com.data.PersonDao;
import co.com.data.dto.Person;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jpatarroyo
 */
@Service("personService")
@Transactional
public class PersonServiceImplementation implements PersonService{

    @Autowired
    private PersonDao personDao;
    
    @Override
    public List<Person> getPeople() {
        return personDao.findPeople();
    }

    @Override
    public Person getPerson(Person person) {
        return personDao.findPersonById(person.getId());
    }

    @Override
    public void addPerson(Person person) {
        personDao.insertPerson(person);
    }

    @Override
    public void updatePerson(Person person) {
        personDao.updatePerson(person);
    }

    @Override
    public void deletePerson(Person person) {
        personDao.deletePerson(person);
    }

    @Override
    public long countPeople() {
        return personDao.countPeople();
    }
    
}
