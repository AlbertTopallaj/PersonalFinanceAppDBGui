package se.albert.personalfinanceguidb.models;

import java.util.Date;
import java.util.UUID;

public class User {

    private UUID id;
    private String username;
    private String password;
    private Date created_at;

    public User(String username, String password, Date created_at){

        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.created_at = created_at;

    }

    public User(UUID id, String username, String password, Date created_at){

        this.id = id;
        this.username = username;
        this.password = password;
        this.created_at = created_at;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
