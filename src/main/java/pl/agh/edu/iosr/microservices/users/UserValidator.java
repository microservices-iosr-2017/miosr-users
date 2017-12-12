package pl.agh.edu.iosr.microservices.users;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

import java.io.UnsupportedEncodingException;

@Component
public class UserValidator {

    private Environment environment;
    private MongoDatabase database;

    public UserValidator(DatabaseUtils utils, Environment environment) {
        this.environment = environment;
        database = utils.initialize();
    }

    public boolean isAValidUser(User user) {
        System.out.println("Starting validating");
        String collectionName = environment.getProperty("spring.data.mongodb.collection");
        MongoCollection<Document> collection = database.getCollection(collectionName);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("username", user.getUsername());
        searchQuery.put("password", user.getPassword());
        Document document = collection.find(searchQuery).first();
        return document != null;
    }


    public void checkToken(String token, User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(environment.getProperty("validator.secretkey"));
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(user.username)
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt.getIssuer());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
