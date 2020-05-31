package com.dev.cinema.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(unique = true)
    private String email;
    @Column(name = "password_hash")
    private String passwordHash;
    private String salt;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + getId()
                + ", email='" + email + '\''
                + ", passwordHash='" + passwordHash + '\''
                + ", salt='" + salt + '\''
                + '}';
    }
}
