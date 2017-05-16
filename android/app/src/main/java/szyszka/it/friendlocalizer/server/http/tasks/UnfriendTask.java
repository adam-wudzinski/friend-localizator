package szyszka.it.friendlocalizer.server.http.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import szyszka.it.friendlocalizer.activities.UserActivity;
import szyszka.it.friendlocalizer.server.http.APIReply;
import szyszka.it.friendlocalizer.server.http.FriedLocatorAPI;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by Squier on 16.05.2017.
 */

public class UnfriendTask extends AsyncTask<Integer, Void, APIReply> {

    public static final String TAG = UnfriendTask.class.getSimpleName();

    private final String UNFRIEND_URL_SUFFIX = "?id=";
    private final String UNFRIEND_URL;

    private FriedLocatorAPI api;
    private UserActivity activity;

    public UnfriendTask(Properties apiConfig, FriedLocatorAPI api, UserActivity activity) {
        this.UNFRIEND_URL = apiConfig.getProperty(FriedLocatorAPI.Keys.FRIENDS_URL_KEY);
        this.api = api;
        this.activity = activity;
    }

    @Override
    protected APIReply doInBackground(Integer... params) {
        try {
            String url = api.API_URL + UNFRIEND_URL + UNFRIEND_URL_SUFFIX + params[0];
            return api.unFriend(new URL(url));
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
                break;
            } default: {
                Log.e(TAG, "Failed to delete friend. HTTP status: " + apiReply.getStatusCode());
            }
        }
    }
}
