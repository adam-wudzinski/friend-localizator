package szyszka.it.friendlocalizer.server.http.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import szyszka.it.friendlocalizer.location.MyLocation;
import szyszka.it.friendlocalizer.server.http.APIReply;
import szyszka.it.friendlocalizer.server.http.FriendLocatorAPI;
import szyszka.it.friendlocalizer.server.users.User;

import static szyszka.it.friendlocalizer.location.MyLocation.getSimpleJSON;

/**
 * Created by Squier on 29.05.2017.
 */

public class ProvideMyLocation extends AsyncTask<MyLocation, Void, APIReply> {

    public static final String TAG = ProvideMyLocation.class.getSimpleName();

    private final String URL;

    private FriendLocatorAPI api;

    public ProvideMyLocation(Properties apiConfig, FriendLocatorAPI api) {
        URL = apiConfig.getProperty(FriendLocatorAPI.Keys.LOCATION_URL_KEY);
        this.api = api;
    }

    @Override
    protected APIReply doInBackground(MyLocation... params) {
        APIReply apiReply = APIReply.NO_REPLY;
            try {
                apiReply = api.provideMyLocation(new URL(api.API_URL + URL), User.Session.KEY, getSimpleJSON(params[0]));
                Log.i(TAG, "Location: " + getSimpleJSON(params[0]));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        return apiReply;
    }
}
