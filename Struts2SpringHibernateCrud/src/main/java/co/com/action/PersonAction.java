package co.com.action;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import co.com.data.dto.Person;
import co.com.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author jpatarroyo
 */
public class PersonAction extends ActionSupport {

    private long peopleCount;
    private List<Person> people;
    private Person person;
    @Autowired
    private PersonService personService;
    Logger log = LogManager.getLogger(PersonAction.class);

    @Override
    public String execute() {
        log.info("Executing method...");
        this.people = personService.getPeople();
        this.peopleCount = personService.countPeople();
        return SUCCESS;
    }

    @Action(value = "/list", results = {
        @Result(name = "person", location = "/WEB-INF/content/person.jsp")})
    public String list() {
        this.people = personService.getPeople();
        return "person";
    }

    @Action(value = "/addPerson", results = {
        @Result(name = "person", location = "/WEB-INF/content/personActions.jsp")})
    public String agregar() {
        person = new Person();
        return "person";
    }

    @Action(value = "/updatePerson", results = {
        @Result(name = "person", location = "/WEB-INF/content/personActions.jsp")})
    public String editar() {
        person = personService.getPerson(person);
        return "person";
    }

    @Action(value = "/deletePerson", results = {
        @Result(name = "success", location = "list", type = "redirect")})
    public String eliminar() {
        person = personService.getPerson(person);
        personService.deletePerson(person);
        return SUCCESS;
    }

    @Action(value = "/savePerson", results = {
        @Result(name = "success", location = "list", type = "redirect")})
    public String guardar() {
        if (person.getId() == null) 
            personService.addPerson(person);
        else
            personService.updatePerson(person);
        return SUCCESS;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public long getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(long peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
