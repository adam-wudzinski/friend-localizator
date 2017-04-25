package com.project.user;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Adas on 2017-04-18.
 */
@NodeEntity
public class User {

    @GraphId
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @Relationship(type = "FRIEND", direction = Relationship.UNDIRECTED)
    public Set<User> friends;

    public void isFriendWith(User user) {
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(user);
    }

    public void unfriendWith(User user) {
        if (friends != null) {
            friends.remove(user);
        }
    }

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String firstname, String lastname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
