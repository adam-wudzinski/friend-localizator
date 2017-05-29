package szyszka.it.friendlocalizer.server.http;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import szyszka.it.friendlocalizer.common.forms.LoginForm;
import szyszka.it.friendlocalizer.server.http.errors.Error;
import szyszka.it.friendlocalizer.server.users.User;

import static szyszka.it.friendlocalizer.server.users.User.getJSON;

/**
 * Created by Squier on 19.04.2017.
 */

public class FriedLocatorAPI implements Parcelable{

    public static final String API_CONFIG = "api_config.properties";
    public final String SEARCH_USERS_URL_SUFFIX = "?search=";

    public static class Keys {
        public static final String API_URL_KEY = "api_url";
        public static final String REG_URL_KEY = "reg_url";
        public static final String GET_USERS_URL_KEY = "get_users_url";
        public static final String LOGIN_URL_KEY = "login_url";
        public static final String FRIENDS_URL_KEY = "on_friends_actions_url";
        public static final String LOCATION_URL_KEY = "location_url";
        public static final String FRIENDS_LOCATIONS_KEY = "friends_locations_url";

        public static final String SEARCH_USERS_SUFFIX = "search_users_suffix";
        public static final String ADD_REMOVE_FRIENDS_SUFFIX = "add_remove_friends_suffix";
        public static final String SHARE_LOCATION_SUFFIX = "share_location_suffix";
    }

    private static final String TAG = FriedLocatorAPI.class.getSimpleName();

    public final String API_URL;
    private HttpURLConnection connection = null;

    public FriedLocatorAPI(Properties apiConfig) {
        API_URL = apiConfig.getProperty(Keys.API_URL_KEY);
    }

    private FriedLocatorAPI(Parcel parcel) {
        API_URL = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(API_URL);
    }

    public static final Parcelable.Creator<FriedLocatorAPI> CREATOR  = new Parcelable.Creator<FriedLocatorAPI>() {
        @Override
        public FriedLocatorAPI createFromParcel(Parcel source) {
            return new FriedLocatorAPI(source);
        }

        @Override
        public FriedLocatorAPI[] newArray(int size) {
            return new FriedLocatorAPI[size];
        }
    };

    public APIReply getAvaibleFriendsLocations(URL getLocations, String sessionKey) {
        BufferedReader reader = null;
        try {
            connection = (HttpURLConnection) getLocations.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", sessionKey);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            return new APIReply(
                    readConnection(reader),
                    connection.getResponseCode(),
                    null
            );

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return APIReply.NO_REPLY;
        } finally {
            closeConnection(null, reader);
        }
    }

    public APIReply provideMyLocation(URL provideMyLocation, String sessionKey, String locationJson) {
        BufferedReader reader = null;
        DataOutputStream writer = null;
        try {
            connection = (HttpURLConnection) provideMyLocation.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", sessionKey);

            writer = new DataOutputStream(connection.getOutputStream());
            writer.writeBytes(locationJson);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            return new APIReply(
                    readConnection(reader),
                    connection.getResponseCode(),
                    null
            );

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return APIReply.NO_REPLY;
        } finally {
            closeConnection(writer, reader);
        }
    }

    public APIReply shareLocation(String requestedMethod, URL shareLocation, String sessionKey) {
        BufferedReader reader = null;
        try {
            connection = (HttpURLConnection) shareLocation.openConnection();
            connection.setRequestMethod(requestedMethod);
            connection.setRequestProperty("Authorization", sessionKey);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            return new APIReply(
                    readConnection(reader),
                    connection.getResponseCode(),
                    null
            );

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return APIReply.NO_REPLY;
        } finally {
            closeConnection(null, reader);
        }

    }

    public APIReply unFriend(URL unfriendURL, String sessionKey) {
        BufferedReader reader = null;
        try {
            connection = (HttpURLConnection) unfriendURL.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", sessionKey);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            return new APIReply(
                    readConnection(reader),
                    connection.getResponseCode(),
                    null
            );

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return APIReply.NO_REPLY;
        } finally {
            closeConnection(null, reader);
        }
    }

    public APIReply addAsFriend(URL addURL, String sessionKey) {
        BufferedReader reader = null;

        try {
            connection = (HttpURLConnection) addURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", sessionKey);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            return new APIReply(
                    readConnection(reader),
                    connection.getResponseCode(),
                    null
            );

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return APIReply.NO_REPLY;
        } finally {
            closeConnection(null, reader);
        }
    }

    public APIReply searchUsers(URL searchURL, String sessionKey) {
        BufferedReader reader = null;
        try {
            connection = (HttpURLConnection) searchURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", sessionKey);
            Log.i(TAG, User.Session.KEY + " " + connection.getResponseCode());

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            return new APIReply(
                    readConnection(reader),
                    connection.getResponseCode(),
                    null
            );

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return APIReply.NO_REPLY;
        } finally {
            closeConnection(null, reader);
        }
    }

    public APIReply registerUser(User user, URL regURL) {
        DataOutputStream writer = null;
        BufferedReader reader = null;
        String userJson = getJSON(user);
        try {
            connection = (HttpURLConnection) regURL.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            writer = new DataOutputStream(connection.getOutputStream());
            writer.writeBytes(userJson);

            return new APIReply(
                    readConnection(reader),
                    connection.getResponseCode(),
                    User.class
                    );

        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return APIReply.NO_REPLY;
        } finally {
            closeConnection(writer, reader);
        }


    }

    public APIReply loginUser(String sessionKey, URL loginUrl) {
        BufferedReader reader = null;
        try {
            connection = (HttpURLConnection) loginUrl.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("Authorization", sessionKey);

            return new APIReply(
                    readConnection(reader),
                    connection.getResponseCode(),
                    User.class
            );

        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return APIReply.NO_REPLY;
        } finally {
            closeConnection(null, reader);
        }
    }

    private String readConnection(BufferedReader reader) throws IOException {
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        Log.i(TAG, "Read data:\n" + response.toString());
        return response.toString();
    }

    private void closeConnection(DataOutputStream writer, BufferedReader reader) {
        if(connection != null) {
            connection.disconnect();
        } if(writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        } if(reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
    }

    public static String getSessionKey(String email, String password) {
        String raw = email + ":" + password;
        return Base64.encodeToString(raw.getBytes(), Base64.DEFAULT);
    }
}