package szyszka.it.friendlocalizer.server.http.tasks;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.activities.UserActivity;
import szyszka.it.friendlocalizer.common.forms.LoginForm;
import szyszka.it.friendlocalizer.server.http.APIReply;
import szyszka.it.friendlocalizer.server.http.FriendLocatorAPI;
import szyszka.it.friendlocalizer.server.http.errors.Error;
import szyszka.it.friendlocalizer.server.users.User;

import static java.net.HttpURLConnection.HTTP_OK;
import static szyszka.it.friendlocalizer.server.http.FriendLocatorAPI.getSessionKey;

/**
 * Created by Squier on 26.04.2017.
 */

public class LoginTask extends AsyncTask<LoginForm, Void, APIReply> {

    public static final String TAG = LoginTask.class.getSimpleName();

    private final String LOGIN_URL;     // on sign in request, friend list will be downloaded
    private final FriendLocatorAPI api;
    private final Context context;

    private String sessionKey;


    public LoginTask(FriendLocatorAPI api, Context context, Properties apiConfig) {
        this.LOGIN_URL = apiConfig.getProperty(FriendLocatorAPI.Keys.LOGIN_URL_KEY);
        this.api = api;
        this.context = context.getApplicationContext();
    }

    @Override
    protected APIReply doInBackground(LoginForm... loginForms) {
        if(loginForms.length > 1) Log.w(TAG, "Note that only one person can sign in.");
        sessionKey = "Basic " + getSessionKey(loginForms[0].getEmail(), loginForms[0].getPassword());
        try {
            return api.loginUser(sessionKey, new URL(api.API_URL + LOGIN_URL));
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return APIReply.NO_REPLY;
    }

    @Override
    protected void onPostExecute(APIReply apiReply) {
        switch (apiReply.getStatusCode()) {
            case HTTP_OK : {
                Log.i(TAG, "HTTP Status code: " + apiReply.getStatusCode());
                Toast.makeText(context, R.string.successfully_signed_in, Toast.LENGTH_SHORT).show();
                User.Session.KEY = sessionKey;

                Intent userActivity = new Intent(context, UserActivity.class);
                userActivity.putExtra("user_data", apiReply.getJSON());
                userActivity.putExtra("api", api);
                userActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(userActivity);
                break;
            } case Error.IO_EXCEPTION : {
                Log.e(TAG, "IOException");
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                break;
            } default : {
                Log.e(TAG, "HTTP Status code: " + String.valueOf(apiReply.getStatusCode()));
                Toast.makeText(context, R.string.http_status_code + String.valueOf(apiReply.getStatusCode()), Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}