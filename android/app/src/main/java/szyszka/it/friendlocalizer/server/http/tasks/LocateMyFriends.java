package szyszka.it.friendlocalizer.server.http.tasks;

import android.os.AsyncTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;
import java.util.Properties;

import szyszka.it.friendlocalizer.server.http.APIReply;
import szyszka.it.friendlocalizer.server.http.FriedLocatorAPI;
import szyszka.it.friendlocalizer.server.users.User;

/**
 * Created by Squier on 29.05.2017.
 */

public class LocateMyFriends extends AsyncTask<Void, Void, APIReply> {

    private final String URL;

    private FriedLocatorAPI api;

    public LocateMyFriends(Properties apiConfig, FriedLocatorAPI api) {
        URL = apiConfig.getProperty(FriedLocatorAPI.Keys.FRIENDS_LOCATIONS_KEY);
        this.api = api;
    }

    @Override
    protected APIReply doInBackground(Void... params) {
        APIReply apiReply =  APIReply.NO_REPLY;
        try {
            apiReply = api.getAvaibleFriendsLocations(new URL(api.API_URL + URL), User.Session.KEY);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return apiReply;
    }
}
