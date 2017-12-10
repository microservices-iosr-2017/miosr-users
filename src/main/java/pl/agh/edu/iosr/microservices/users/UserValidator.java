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

    private final MongoClient mongoClient;
    private final MongoDatabase db;


    public UserValidator(Environment environment) {
        this.environment = environment;
        String databaseHost = environment.getProperty("spring.data.mongodb.host");
        String databasePort = environment.getProperty("spring.data.mongodb.port");
        mongoClient = new MongoClient(databaseHost, Integer.parseInt(databasePort));
        db = mongoClient.getDatabase("users");
    }

    public boolean isAValidUser(User user) {
        System.out.println("Yes, its valid");
        String databaseCollection = environment.getProperty("spring.data.mongodb.database");
        MongoCollection<Document> collection = db.getCollection(databaseCollection);

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
