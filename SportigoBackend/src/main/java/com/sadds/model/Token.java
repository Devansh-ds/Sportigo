package com.sadds.model;

import jakarta.persistence.*;
import org.hibernate.type.TrueFalseConverter;

@Entity
@Table(name = "jwt_token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;

    @Convert(converter = TrueFalseConverter.class)
    private boolean expired;
    @Convert(converter = TrueFalseConverter.class)
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Token() {}

    public Token(String token,
                 boolean expired,
                 boolean revoked,
                 User user) {
        this.token = token;
        this.expired = expired;
        this.revoked = revoked;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expired=" + expired +
                ", revoked=" + revoked +
                ", user=" + user +
                '}';
    }
}




















