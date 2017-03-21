package application.controller;

import application.model.Person;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private PersonRepository personRepository;

    public Person saveToDB(Person person) {
        Person savedPerson = personRepository.save(person);
        logger.info("Entity saved");
        return savedPerson;
    }

}