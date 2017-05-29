package szyszka.it.friendlocalizer.server.http.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import szyszka.it.friendlocalizer.activities.adapters.RequiredSearch;
import szyszka.it.friendlocalizer.activities.adapters.UserAdapter;
import szyszka.it.friendlocalizer.server.http.APIReply;
import szyszka.it.friendlocalizer.server.http.FriedLocatorAPI;
import szyszka.it.friendlocalizer.server.users.User;
import szyszka.it.friendlocalizer.server.users.UserDTO;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by Squier on 26.04.2017.
 */

public class SearchUsersTask extends AsyncTask<RequiredSearch, Void, APIReply> {

    private static final String TAG = SearchUsersTask.class.getSimpleName();
    private static final String API_SEARCH_TARGET = "search_target";
    private final String GET_USERS_URL;
    private final String GET_FRIENDS_URL;

    private FriedLocatorAPI api;
    private ListView list;
    private UserAdapter adapter;

    public SearchUsersTask(Properties apiConfig, FriedLocatorAPI api) {
        GET_USERS_URL = apiConfig.getProperty(FriedLocatorAPI.Keys.GET_USERS_URL_KEY);
        GET_FRIENDS_URL = apiConfig.getProperty(FriedLocatorAPI.Keys.FRIENDS_URL_KEY);
        this.api = api;
    }

    @Override
    protected APIReply doInBackground(RequiredSearch... params) {

        switch (params[0].getRequirement()) {
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
                adapter.clearDataSet();
                adapter.addAllToDataSet(users);
            }
        }
    }

    private APIReply getAPIReply(String searchURL, RequiredSearch extraResource) {
        APIReply reply = null;
        try {
            switch (extraResource.getRequirement()) {
                case FRIENDS: {
                    reply = api.searchUsers(new URL(api.API_URL + searchURL), User.Session.KEY);
                    break;
                }
                case USERS: {
                    String searchPhrase = extraResource.getSearchPhrase();
                    searchURL = searchPhrase == null ? searchURL : searchURL + api.SEARCH_USERS_URL_SUFFIX + searchPhrase;

                    reply = api.searchUsers(new URL(api.API_URL + searchURL), User.Session.KEY);
                    break;
                }
            }
            reply.putExtra(API_SEARCH_TARGET, extraResource);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }
        return reply;
    }

    public void setList(ListView list) {
        this.list = list;
    }

    public void setListAdapter() {
        list.setAdapter(adapter);
    }

    public UserAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(UserAdapter adapter) {
        this.adapter = adapter;
    }
}
