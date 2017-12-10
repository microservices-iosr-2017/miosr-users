package pl.agh.edu.iosr.microservices.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/login")
public class UsersController {

    @Autowired
    private UserValidator validator;

    @RequestMapping(method = POST)
    @ResponseBody
    public String login(@RequestBody User user) {
        System.out.println("HEEEEEEEEEEEEEELO from " + user.getUsername() + " " + user.getPassword());
        if (validator.isAValidUser(user)) {
            return "{\"username\":\"myname2\",\"password\":\"mypassword2\"}";
        }
        return "";
    }
}
