package pl.agh.edu.iosr.microservices.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<Object> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("HEEEEEEEEEEEEEELO from " + user.getUsername() + " " + user.getPassword());
        if (validator.isAValidUser(user)) {
            String token = generator.generate(user);
            validator.checkToken(token, user);
            response.setHeader("token", token);
            System.out.println("New2");
            return ResponseEntity.status(HttpServletResponse.SC_ACCEPTED).body(null);
        }
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(null);
    }

    @RequestMapping(method = POST, value = "/register")
    public void register(@RequestBody User newUser) {
        System.out.println("Hello from new user");
        registrator.registerUser(newUser);

    }
}
