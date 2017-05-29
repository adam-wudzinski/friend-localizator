package szyszka.it.friendlocalizer.server.http.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import szyszka.it.friendlocalizer.activities.UserActivity;
import szyszka.it.friendlocalizer.server.http.APIReply;
import szyszka.it.friendlocalizer.server.http.FriedLocatorAPI;
import szyszka.it.friendlocalizer.server.users.User;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by Squier on 16.05.2017.
 */

public class UnfriendTask extends AsyncTask<Integer, Void, APIReply> {

    public static final String TAG = UnfriendTask.class.getSimpleName();

    private final String SUFFIX;
    private final String URL;

    private FriedLocatorAPI api;
    private UserActivity activity;

    public UnfriendTask(Properties apiConfig, FriedLocatorAPI api, UserActivity activity) {
        this.URL = apiConfig.getProperty(FriedLocatorAPI.Keys.FRIENDS_URL_KEY);
        this.SUFFIX = apiConfig.getProperty(FriedLocatorAPI.Keys.ADD_REMOVE_FRIENDS_SUFFIX);
        this.api = api;
        this.activity = activity;
    }

    @Override
    protected APIReply doInBackground(Integer... params) {
        try {
            String url = api.API_URL + URL + SUFFIX + params[0];
            return api.unFriend(new URL(url), User.Session.KEY);
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
                activity.setCurrentPage(UserActivity.MY_FRIENDS_PAGE);
                break;
            } default: {
                Log.e(TAG, "Failed to delete friend. HTTP status: " + apiReply.getStatusCode());
            }
        }
    }
}
