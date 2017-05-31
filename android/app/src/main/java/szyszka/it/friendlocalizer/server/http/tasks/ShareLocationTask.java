package szyszka.it.friendlocalizer.server.http.tasks;

import android.os.AsyncTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import szyszka.it.friendlocalizer.server.http.APIReply;
import szyszka.it.friendlocalizer.server.http.FriendLocatorAPI;
import szyszka.it.friendlocalizer.server.users.User;

/**
 * Created by Squier on 29.05.2017.
 */

public class ShareLocationTask extends AsyncTask<Integer, Void, APIReply> {

    public static final String TAG = ShareLocationTask.class.getSimpleName();
    private final String URL;
    private final String SUFFIX;

    private FriendLocatorAPI api;
    private String requestedMethod;

    public ShareLocationTask(Properties apiConfig, FriendLocatorAPI api, String requestedMethod) {
        URL = apiConfig.getProperty(FriendLocatorAPI.Keys.LOCATION_URL_KEY);
        SUFFIX = apiConfig.getProperty(FriendLocatorAPI.Keys.SHARE_LOCATION_SUFFIX);
        this.api = api;
        this.requestedMethod = requestedMethod;
    }

    @Override
    protected APIReply doInBackground(Integer... params) {
        APIReply apiReply = APIReply.NO_REPLY;
        try {
            apiReply = api.shareLocation(requestedMethod, new URL(api.API_URL + URL + SUFFIX + params[0]), User.Session.KEY);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return apiReply;
    }



}
