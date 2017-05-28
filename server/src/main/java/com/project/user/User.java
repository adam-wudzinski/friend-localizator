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

    private String name;
    private String surname;
    private String email;
    private String password;
    private Double latitude;
    private Double longitude;

    @Relationship(type = "FRIEND", direction = Relationship.UNDIRECTED)
    public Set<User> friends;

    @Relationship(type = "SHARES_LOCALIZATION", direction = Relationship.OUTGOING)
    public Set<User> shares;

    public void friendWith(User user) {
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

    public boolean isSharingLocationWith(User user) {
        if (shares != null) {
            return shares.stream()
                    .filter(x -> x.getId() == user.getId())
                    .count() == 1;
        }
        return false;
    }

    public void shareLocationWith(User user){
        if (shares == null) {
            shares = new HashSet<>();
        }
        shares.add(user);
    }

    public void unshareLocationWith(User user){
        if (shares != null) {
            shares.remove(user);
        }
    }

    public User() {
        this.friends = new HashSet<>();
        this.shares = new HashSet<>();
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.friends = new HashSet<>();
        this.shares = new HashSet<>();
    }

    public User(String email, String password, String name, String surname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.friends = new HashSet<>();
        this.shares = new HashSet<>();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public boolean isFriendWith(Long id) {
        if (friends == null || friends.isEmpty()) {
            return false;
        }
        return friends.stream()
                .filter(f -> f.getId().equals(id))
                .count() == 1;
    }

    public Set<User> getShares() {
        return shares;
    }
}
