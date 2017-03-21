package application.things;

import org.jboss.logging.Logger;

@org.springframework.stereotype.Service
public class Service {

    private Logger logger = Logger.getLogger(this.getClass());
    private static String DEEPLINK_SCHEMA = "tsdl";

    public String saveEntityToDB() {
       return "";
    }

}