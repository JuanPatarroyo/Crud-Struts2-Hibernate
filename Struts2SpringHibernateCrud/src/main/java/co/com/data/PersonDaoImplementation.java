/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.data;

import co.com.data.dto.Person;
import java.util.List;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jpatarroyo
 */
@Repository
public class PersonDaoImplementation implements PersonDao {

    Logger log = LogManager.getRootLogger();

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertPerson(Person person) {
        entityManager.persist(person);
    }

    @Override
    public void updatePerson(Person person) {
        entityManager.merge(person);
    }

    @Override
    public void deletePerson(Person person) {
        entityManager.remove(entityManager.merge(person));
    }

    @Override
    public Person findPersonById(long idPerson) {
        return entityManager.find(Person.class, idPerson);
    }

    @Override
    public List<Person> findPeople() {
        String jpql = "SELECT p FROM Person p";
        Query query = entityManager.createQuery(jpql);
        //Forzar a ir directamente a la base de datos para refrescar datos
        query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        List<Person> people = query.getResultList();
        System.out.println("people: " + people);
        return people;
    }

    @Override
    public long countPeople() {
        String consulta = "select count(p) from Person p";
        Query q = entityManager.createQuery(consulta);
        long contador = (long) q.getSingleResult();
        return contador;
    }

    @Override
    public Person getPersonByEmail(Person person) {
        String cadena = "%" + person.getEmail() + "%"; //se usa en el like como caracteres especiales
        String consulta = "from Person p where upper(p.email) like upper(:param1)";
        Query q = entityManager.createQuery(consulta);
        q.setParameter("param1", cadena);
        return (Person) q.getSingleResult();
    }
}
