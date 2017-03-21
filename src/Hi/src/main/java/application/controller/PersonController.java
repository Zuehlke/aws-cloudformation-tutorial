package application.controller;

import application.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("to-vault")
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String saveSomethingToDB() {
        final Person johannes = new Person();
        johannes.setName("Johannes Banannes");
        johannes.setAge("23");

        final Person savedJohannes = personService.saveToDB(johannes);

        return "Gratz, you sent a Johannes to the vault! :) This is his id: " + savedJohannes.getId();
    }

}
