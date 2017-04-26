package szyszka.it.friendlocalizer.server.http.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import szyszka.it.friendlocalizer.activities.adapters.UsersAdapter;
import szyszka.it.friendlocalizer.server.http.APIReply;
import szyszka.it.friendlocalizer.server.http.FriedLocatorAPI;
import szyszka.it.friendlocalizer.server.users.UserDTO;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by Squier on 26.04.2017.
 */

public class SearchUsersTask extends AsyncTask<String, Void, APIReply> {

    private static final String TAG = SearchUsersTask.class.getSimpleName();
    public final String GET_USERS_URL;

    private FriedLocatorAPI api;
    private ListView list;
    private Context context;

    public SearchUsersTask(Properties apiConfig, FriedLocatorAPI api, Context context) {
        GET_USERS_URL = apiConfig.getProperty(FriedLocatorAPI.Keys.GET_USERS_URL_KEY);
        this.api = api;
        this.context = context;
    }

    @Override
    protected APIReply doInBackground(String... params) {
        try {
            return api.searchUsers(null, new URL(api.API_URL + GET_USERS_URL));
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(APIReply apiReply) {
        switch (apiReply.getStatusCode()) {
            case HTTP_OK : {
                List<UserDTO> users = UserDTO.arrayFromJSON(apiReply.getJSON());
                list.setAdapter(new UsersAdapter(context, 0, users));
            }
        }
    }

    public void setList(ListView list) {
        this.list = list;
    }
}
