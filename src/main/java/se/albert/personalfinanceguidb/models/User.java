package se.albert.personalfinanceguidb.models;

import java.util.Date;
import java.util.UUID;

public class User {

    // UUID id för användaren
    private UUID id;

    // Användarnamn för användaren
    private String username;

    // Lösenord för användarnamn
    private String password;

    // När användaren skapades
    private Date created_at;

    // Konstruktor för user
    public User(UUID id, String username, String password, Date created_at){

        this.id = id;
        this.username = username;
        this.password = password;
        this.created_at = created_at;

    }

    // Konstuktor för users när de för första gången registeras
    public User(String username, String password){

        this.id = UUID.randomUUID();
        this.created_at = new Date();
        this.username = username;
        this.password = password;

    }


    // Getters och setters

    public Date getCreated_at(){
        return created_at;
    }

    public void setCreated_at(Date created_at){
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
