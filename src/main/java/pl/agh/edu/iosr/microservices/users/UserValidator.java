package pl.agh.edu.iosr.microservices.users;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

@Component
public class UserValidator {

    private Environment environment;

    @Autowired
    private MongoTemplate mongoTemplate;

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
}
