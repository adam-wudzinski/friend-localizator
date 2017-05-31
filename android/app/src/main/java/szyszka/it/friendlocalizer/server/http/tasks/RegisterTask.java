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
import szyszka.it.friendlocalizer.activities.HelloActivity;
import szyszka.it.friendlocalizer.server.http.APIReply;
import szyszka.it.friendlocalizer.server.http.FriendLocatorAPI;
import szyszka.it.friendlocalizer.server.http.errors.Error;
import szyszka.it.friendlocalizer.server.users.User;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by Squier on 26.04.2017.
 */

public class RegisterTask extends AsyncTask<User, Void, APIReply> {

    public static final String TAG = RegisterTask.class.getSimpleName();
    private final String REG_URL;

    private FriendLocatorAPI api;
    private Context context;

    public RegisterTask(FriendLocatorAPI api, Context context, Properties apiConfig) {
        this.api = api;
        this.context = context.getApplicationContext();
        REG_URL = apiConfig.getProperty(FriendLocatorAPI.Keys.REG_URL_KEY);
    }

    @Override
    protected APIReply doInBackground(User... users) {
        if(users.length > 1) {
            Log.w(TAG, "Note that only first user will be registered!");
        }
        try {
            return api.registerUser(users[0], new URL(api.API_URL + REG_URL));
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return APIReply.NO_REPLY;
        }
    }

    @Override
    protected void onPostExecute(APIReply apiReply) {
        switch (apiReply.getStatusCode()) {
            case HTTP_OK : {
                Toast.makeText(context, R.string.successfully_registered, Toast.LENGTH_LONG).show();
                Intent helloActivity = new Intent(context, HelloActivity.class);
                helloActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(helloActivity);
                break;
            }case Error.IO_EXCEPTION : {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                break;
            } default: {
                Toast.makeText(context, "Http status: " + String.valueOf(apiReply), Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
