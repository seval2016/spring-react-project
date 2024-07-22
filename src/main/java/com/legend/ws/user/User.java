package com.legend.ws.user;

import com.legend.ws.user.validation.UniqueEmail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users",uniqueConstraints =@UniqueConstraint(columnNames = {"email"}) )
public class User {
    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    @Size(min=4, max=255)
    private String username;

    @NotBlank
    @Email
    @UniqueEmail
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    @Size(min=4, max=255)
    private String password;

  


    // Getter for id
    public long getId() {
        return id;
    }

    // Setter for id
    public void setId(long id) {
        this.id = id;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }
}
