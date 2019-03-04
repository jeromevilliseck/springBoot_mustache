package app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringBootWebApplication {

    // inject via application.properties
    @Value("${app.welcome.message}")
    private String MESSAGE = "";

    /*Affecte le membre title avec la valeur de la variable title contenue dans le fichier
    application.properties
     */
    @Value("${app.welcome.title}")
    private String TITLE = "";

    //Construire une Map d'objet qui seront diffusés dans la page html welcome
    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("title", TITLE);
        model.put("message", MESSAGE);
        return "welcome";
    }

    // test 5xx errors
    @RequestMapping("/5xx")
    public String ServiceUnavailable() {
        throw new RuntimeException("ABC");
    }
}
