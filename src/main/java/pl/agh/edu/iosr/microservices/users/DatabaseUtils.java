package pl.agh.edu.iosr.microservices.users;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtils {

    public DatabaseUtils(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    private Environment environment;

    public MongoDatabase initialize() {
        String databaseHost = environment.getProperty("spring.data.mongodb.host");
        String databasePort = environment.getProperty("spring.data.mongodb.port");
        MongoClient mongoClient = new MongoClient(databaseHost, Integer.parseInt(databasePort));
        String databaseName = environment.getProperty("spring.data.mongodb.database");
        MongoDatabase database = mongoClient.getDatabase(databaseName);

        String collectionName = environment.getProperty("spring.data.mongodb.collection");
        boolean collectionExists = false;
        MongoIterable<String> collections = database.listCollectionNames();
        MongoCursor<String> iterator = collections.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().equals(collectionName)) {
                collectionExists = true;
                break;
            }
        }

        if (!collectionExists) {
            database.createCollection(collectionName);
        }

        return database;
    }
}
