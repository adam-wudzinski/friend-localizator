package szyszka.it.friendlocalizer.location;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import szyszka.it.friendlocalizer.server.users.LocalizedUser;

/**
 * Created by Squier on 29.05.2017.
 */

public class Locations {

    public static final String TAG = Locations.class.getSimpleName();

    private MyLocation userLocation;
    private Marker userMarker;
    private ArrayList<LocalizedUser> friendsLocations;
    private ArrayList<Marker> friendsMarkers;
    private GoogleMap map;

    public Locations() {
    }

    public Locations(MyLocation userLocation, ArrayList<LocalizedUser> friendsLocations, ArrayList<Marker> friendsMarkers) {
        this.userLocation = userLocation;
        this.friendsLocations = friendsLocations;
        this.friendsMarkers = friendsMarkers;
    }

    public MyLocation getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(MyLocation userLocation) {
        this.userLocation = userLocation;
    }

    public ArrayList<LocalizedUser> getFriendsLocations() {
        return friendsLocations;
    }

    public void setFriendsLocations(ArrayList<LocalizedUser> friendsLocations) {
        this.friendsLocations = friendsLocations;
    }

    public void clearFriendsLocations() {
        friendsLocations.clear();
    }

    public void addFriend(LocalizedUser friend) {
        friendsLocations.add(friend);
    }

    public GoogleMap getMap() {
        return map;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public Marker getUserMarker() {
        return userMarker;
    }

    public void setUserMarker(Marker userMarker) {
        this.userMarker = userMarker;
    }

    public ArrayList<Marker> getFriendsMarkers() {
        return friendsMarkers;
    }

    public void setFriendsMarkers(ArrayList<Marker> friendsMarkers) {
        this.friendsMarkers = friendsMarkers;
    }

    public void removeAllMarkers() {
        if(userMarker != null) {
            userMarker.remove();
        }

        for (Marker marker : friendsMarkers) {
            if (marker != null) marker.remove();
        }
    }

    public void addMarker(Marker marker) {
        friendsMarkers.add(marker);
    }

    public void setMarker(Marker marker, int position) {
        Log.i(TAG, "Index: " + position + " ArraySize: " + friendsMarkers.size());
        if(friendsMarkers.isEmpty() || position >= friendsMarkers.size()) {
            friendsMarkers.add(marker);
        } else {
            friendsMarkers.remove(position);
            friendsMarkers.add(position, marker);
        }
    }

}
