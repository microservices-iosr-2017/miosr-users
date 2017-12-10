package pl.agh.edu.iosr.microservices.users;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

@Component
public class UserValidator {

    @Autowired
    private Environment environment;

    private final MongoClient mongoClient;
    private final MongoDatabase db;


    public UserValidator() {
        String databaseHost = environment.getProperty("spring.data.mongodb.host");
        String databasePort = environment.getProperty("spring.data.mongodb.port");
        mongoClient = new MongoClient(databaseHost, Integer.parseInt(databasePort));
        db = mongoClient.getDatabase("users");
    }

    public boolean isAValidUser(User user) {
        System.out.println("Yes, its valid");
        String databaseCollection = environment.getProperty("spring.data.mongodb.database");
        DBCollection table = (DBCollection) db.getCollection(databaseCollection);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("username", user.getUsername());
        searchQuery.put("password", user.getPassword());
        DBCursor cursor = table.find(searchQuery);

        if (!cursor.hasNext()) {
            System.out.println("No users found");
        } else {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        }


        return true;
    }
}
