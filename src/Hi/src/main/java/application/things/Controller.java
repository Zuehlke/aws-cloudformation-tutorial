package application.things;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
@RequestMapping("hi")
public class Controller {

    @Autowired
    private Service service;

//    @Autowired
//    private DataSource dataSource;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String saveSomethingToDB() {
        return "You most probably saved something to DB.";
    }

}
