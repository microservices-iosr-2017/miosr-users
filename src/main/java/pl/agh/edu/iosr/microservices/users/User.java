package pl.agh.edu.iosr.microservices.users;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    public String username;

    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
