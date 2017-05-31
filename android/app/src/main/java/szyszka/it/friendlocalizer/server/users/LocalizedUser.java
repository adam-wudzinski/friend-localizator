package szyszka.it.friendlocalizer.server.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import szyszka.it.friendlocalizer.location.MyLocation;

/**
 * Created by Squier on 29.05.2017.
 */

public class LocalizedUser {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private Double latitude;
    private Double longitude;

    public LocalizedUser(Long id, String name, String surname, String email, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocalizedUser() {
    }

    public static List<LocalizedUser> getFromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, new TypeReference<List<LocalizedUser>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
