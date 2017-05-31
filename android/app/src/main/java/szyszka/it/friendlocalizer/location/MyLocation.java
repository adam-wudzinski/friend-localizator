package szyszka.it.friendlocalizer.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Squier on 29.05.2017.
 */

public class MyLocation {

    private double latitude;
    private double longitude;

    public MyLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public MyLocation() {
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static String getSimpleJSON(MyLocation myLocation) {
        return "{\"latitude\":" + myLocation.getLatitude() + ",\"longitude\":" + myLocation.getLongitude() + "}";
    }

    public static String getJSON(MyLocation myLocation) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(myLocation);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    @Override
    public String toString() {
        return "MyLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

}
