package pl.agh.edu.iosr.microservices.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/login")
public class UsersController {

    @Autowired
    private UserValidator validator;

    @Autowired
    private JWTTokenGenerator generator;

    @RequestMapping(method = POST)
    @ResponseBody
//    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String login(@RequestBody User user) {
        System.out.println("HEEEEEEEEEEEEEELO from " + user.getUsername() + " " + user.getPassword());
        if (validator.isAValidUser(user)) {
            String token = generator.generate(user);
            validator.checkToken(token, user);
            return token;
        }
        return String.valueOf(HttpServletResponse.SC_FORBIDDEN);
    }
}
