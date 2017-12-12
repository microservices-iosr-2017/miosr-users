package pl.agh.edu.iosr.microservices.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class UsersController {

    @Autowired
    private UserValidator validator;

    @Autowired
    private UserRegistrator registrator;

    @Autowired
    private JWTTokenGenerator generator;

    @RequestMapping(method = POST, value="/login")
    @ResponseBody
    public String login(@RequestBody User user) {
        System.out.println("HEEEEEEEEEEEEEELO from " + user.getUsername() + " " + user.getPassword());
        if (validator.isAValidUser(user)) {
            String token = generator.generate(user);
            validator.checkToken(token, user);
            return token;
        }
        return String.valueOf(HttpServletResponse.SC_FORBIDDEN);
    }

    @RequestMapping(method = POST, value = "/register")
    public void register(@RequestBody User newUser) {
        System.out.println("Hello from new user");
        registrator.registerUser(newUser);

    }
}
