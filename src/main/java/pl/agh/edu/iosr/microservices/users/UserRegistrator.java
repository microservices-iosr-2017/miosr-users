package pl.agh.edu.iosr.microservices.users;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrator {

    private final Environment environment;
    private MongoDatabase database;


    public UserRegistrator(DatabaseUtils databaseUtils, Environment environment) {
        this.environment = environment;
        database = databaseUtils.initialize();
    }

    public void registerUser(User newUser) {
        addUser(newUser);
    }

    private void addUser(User newUser) {
        String collectionName = environment.getProperty("spring.data.mongodb.collection");
        MongoCollection<Document> collection = database.getCollection(collectionName);

        Document document = new Document();
        document.append("username", newUser.getUsername());
        document.append("password", newUser.getPassword());
        collection.insertOne(document);
        System.out.println("Successfully inserted");
    }
}
