package szyszka.it.friendlocalizer.server.http.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import szyszka.it.friendlocalizer.location.Locations;
import szyszka.it.friendlocalizer.server.http.APIReply;
import szyszka.it.friendlocalizer.server.http.FriendLocatorAPI;
import szyszka.it.friendlocalizer.server.users.LocalizedUser;
import szyszka.it.friendlocalizer.server.users.User;

/**
 * Created by Squier on 29.05.2017.
 */

public class LocateMyFriends extends AsyncTask<Void, Void, APIReply> {

    public static final String TAG = LocateMyFriends.class.getSimpleName();

    private final String URL;

    private FriendLocatorAPI api;
    private Locations locations;
    private List<LocalizedUser> friends;

    public LocateMyFriends(Properties apiConfig, FriendLocatorAPI api, Locations locations) {
        URL = apiConfig.getProperty(FriendLocatorAPI.Keys.FRIENDS_LOCATIONS_KEY);
        this.api = api;
        this.locations = locations;
    }

    @Override
    protected void onPreExecute() {
        locations.removeAllMarkers();
    }

    @Override
    protected APIReply doInBackground(Void... params) {
        APIReply apiReply =  APIReply.NO_REPLY;
        try {
            apiReply = api.getAvaibleFriendsLocations(new URL(api.API_URL + URL), User.Session.KEY);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        friends = LocalizedUser.getFromJson(apiReply.getJSON());

        return apiReply;
    }

    @Override
    protected void onPostExecute(APIReply apiReply) {
        markUserOnMap();
        try {
            markFriendsOnMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void markFriendsOnMap() {
        if(friends != null) {
            locations.setFriendsLocations((ArrayList<LocalizedUser>) friends);
            int index = 0;
            for (LocalizedUser user : friends) {
                Log.i(TAG, "Located friend: " + user.getEmail());
                Marker friendMarker = locations.getMap().addMarker(new MarkerOptions()
                        .position(new LatLng(user.getLatitude(), user.getLongitude()))
                        .title(user.getName() + " " + user.getSurname())
                );
                friendMarker.showInfoWindow();
                locations.setMarker(friendMarker, index);
            }
        }
    }

    private void markUserOnMap() {
        try {
            locations.setUserMarker(
                    locations.getMap().addMarker(new MarkerOptions()
                            .position(locations.getUserLocation().getLatLng())
                            .title("TO TY!")
                    )
            );
            locations.getUserMarker().showInfoWindow();
        } catch (Exception e) {
            Log.e(TAG, "Failed to mark your position: ", e);
        }
    }
}
