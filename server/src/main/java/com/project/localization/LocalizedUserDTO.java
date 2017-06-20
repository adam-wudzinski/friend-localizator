package com.project.localization;

import com.project.user.User;

/**
 * User with localization DTO class
 */
public class LocalizedUserDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Double latitude;
    private Double longitude;

    public LocalizedUserDTO(User user) {
        if (user != null) {
            this.id = user.getId();
            this.name = user.getName();
            this.surname = user.getSurname();
            this.email = user.getEmail();
            this.latitude = user.getLatitude();
            this.longitude = user.getLongitude();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
