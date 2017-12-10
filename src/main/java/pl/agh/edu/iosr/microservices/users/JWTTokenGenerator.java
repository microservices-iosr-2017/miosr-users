package pl.agh.edu.iosr.microservices.users;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class JWTTokenGenerator {

    @Autowired
    private Environment environment;


    public String generate(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(environment.getProperty("validator.secretkey"));
            String token = JWT.create()
                    .withIssuer(user.username)
                    .sign(algorithm);
            return token;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
