package szyszka.it.friendlocalizer.server.http.tasks;

import android.os.AsyncTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import szyszka.it.friendlocalizer.location.MyLocation;
import szyszka.it.friendlocalizer.server.http.APIReply;
import szyszka.it.friendlocalizer.server.http.FriedLocatorAPI;
import szyszka.it.friendlocalizer.server.users.User;

import static szyszka.it.friendlocalizer.location.MyLocation.getJSON;

/**
 * Created by Squier on 29.05.2017.
 */

public class ProvideMyLocation extends AsyncTask<MyLocation, Void, APIReply> {

    private final String URL;

    private FriedLocatorAPI api;

    public ProvideMyLocation(Properties apiConfig, FriedLocatorAPI api) {
        URL = apiConfig.getProperty(FriedLocatorAPI.Keys.LOCATION_URL_KEY);
        this.api = api;
    }

    @Override
    protected APIReply doInBackground(MyLocation... params) {
        APIReply apiReply = APIReply.NO_REPLY;
            try {
                apiReply = api.provideMyLocation(new URL(api.API_URL + URL), User.Session.KEY, getJSON(params[0]));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        return apiReply;
    }
}
