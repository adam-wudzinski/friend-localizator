package szyszka.it.friendlocalizer.server.http.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import szyszka.it.friendlocalizer.activities.UserActivity;
import szyszka.it.friendlocalizer.activities.adapters.AllUsersAdapter;
import szyszka.it.friendlocalizer.activities.adapters.IllegalRequirementException;
import szyszka.it.friendlocalizer.activities.adapters.RequiredSearch;
import szyszka.it.friendlocalizer.activities.adapters.UserAdapterFactory;
import szyszka.it.friendlocalizer.activities.fragments.UserFriendsFragments;
import szyszka.it.friendlocalizer.server.http.APIReply;
import szyszka.it.friendlocalizer.server.http.FriedLocatorAPI;
import szyszka.it.friendlocalizer.server.users.UserDTO;

import static java.net.HttpURLConnection.HTTP_OK;
import static szyszka.it.friendlocalizer.activities.fragments.UserFriendsFragments.*;

/**
 * Created by Squier on 26.04.2017.
 */

public class SearchUsersTask extends AsyncTask<RequiredSearch, Void, APIReply> {

    private static final String TAG = SearchUsersTask.class.getSimpleName();
    public final String GET_USERS_URL;
    public final String GET_FRIENDS_URL;

    private FriedLocatorAPI api;
    private ListView list;
    private Context context;

    public SearchUsersTask(Properties apiConfig, FriedLocatorAPI api, Context context) {
        GET_USERS_URL = apiConfig.getProperty(FriedLocatorAPI.Keys.GET_USERS_URL_KEY);
        GET_FRIENDS_URL = apiConfig.getProperty(FriedLocatorAPI.Keys.FRIENDS_URL_KEY);
        this.api = api;
        this.context = context;
    }

    @Override
    protected APIReply doInBackground(RequiredSearch... params) {

        switch (params[0]) {
            case FRIENDS: {
                return getAPIReply(GET_FRIENDS_URL, params[0]);
            }
            case USERS: {
                return getAPIReply(GET_USERS_URL, params[0]);
            }
            default: {
                Log.e(TAG, "Illegal option");
                return null;
            }
        }

    }

    @Override
    protected void onPostExecute(APIReply apiReply) {
        switch (apiReply.getStatusCode()) {
            case HTTP_OK : {
                List<UserDTO> users = UserDTO.arrayFromJSON(apiReply.getJSON());
                RequiredSearch requirement = (RequiredSearch) apiReply.getExtraObject(API_SEARCH_TARGET);
                try {
                    list.setAdapter(
                            UserAdapterFactory.getUserAdapter(
                                    requirement,
                                    context,
                                    users
                            )
                    );
                } catch (IllegalRequirementException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    private APIReply getAPIReply(String searchTarget, RequiredSearch extraResource) {
        APIReply reply = null;
        try {
            reply = api.searchUsers(null, new URL(api.API_URL + searchTarget));
            reply.putExtra(API_SEARCH_TARGET, extraResource);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }
        return reply;
    }

    public void setList(ListView list) {
        this.list = list;
    }
}
