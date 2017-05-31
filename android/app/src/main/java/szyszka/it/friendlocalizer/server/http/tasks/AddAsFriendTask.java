package szyszka.it.friendlocalizer.server.http.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import szyszka.it.friendlocalizer.activities.UserActivity;
import szyszka.it.friendlocalizer.server.http.APIReply;
import szyszka.it.friendlocalizer.server.http.FriendLocatorAPI;
import szyszka.it.friendlocalizer.server.users.User;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by Squier on 15.05.2017.
 */

public class AddAsFriendTask extends AsyncTask<Integer, Void, APIReply> {

    public static final String TAG = AddAsFriendTask.class.getSimpleName();

    private final String ADD_FRIEND_URL_SUFFIX = "?id=";
    private final String ADD_FRIEND_URL;

    private FriendLocatorAPI api;
    private UserActivity activity;

    public AddAsFriendTask(Properties apiConfig, FriendLocatorAPI api, UserActivity activity) {
        this.ADD_FRIEND_URL = apiConfig.getProperty(FriendLocatorAPI.Keys.FRIENDS_URL_KEY);
        this.api = api;
        this.activity = activity;
    }

    @Override
    protected APIReply doInBackground(Integer... params) {
        try {
            String url = api.API_URL + ADD_FRIEND_URL + ADD_FRIEND_URL_SUFFIX + params[0];
            return api.addAsFriend(new URL(url), User.Session.KEY);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
            return APIReply.NO_REPLY;
        }
    }

    @Override
    protected void onPostExecute(APIReply apiReply) {
        switch (apiReply.getStatusCode()) {
            case HTTP_OK: {
                activity.reloadPageAdapter();
                activity.setCurrentPage(UserActivity.SEARCH_FOR_FRIENDS_PAGE);
                break;
            }
            default: {
                Log.e(TAG, "Failed to add friend. HTTP status code: " + apiReply.getStatusCode());
            }
        }
    }
}
