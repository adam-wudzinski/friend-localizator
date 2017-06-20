package com.project.user;

/**
 * User DTO class
 */
public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;

    public UserDTO(User user) {
        if (user != null) {
            this.id = user.getId();
            this.name = user.getName();
            this.surname = user.getSurname();
            this.email = user.getEmail();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
