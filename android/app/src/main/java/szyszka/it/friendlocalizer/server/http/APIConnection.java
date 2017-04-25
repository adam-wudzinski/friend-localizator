package szyszka.it.friendlocalizer.server.http;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import szyszka.it.friendlocalizer.common.forms.LoginForm;
import szyszka.it.friendlocalizer.server.users.User;

import static szyszka.it.friendlocalizer.server.users.User.getJSON;

/**
 * Created by Squier on 19.04.2017.
 */

public class APIConnection {

    public static final String API_URL = "http://192.168.0.5:8080";
    public static final String REG_LINK = "/auth/register";

    private String apiEndpoint;

    public APIConnection() {
    }

    public APIConnection(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public int registerUser(User user, URL regURL) throws IOException {
        String userJson = getJSON(user);
        HttpURLConnection connection = (HttpURLConnection) regURL.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
        writer.writeBytes(userJson);
        return connection.getResponseCode();
    }

    public int loginUser(LoginForm loginForm, URL loginUrl) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) loginUrl.openConnection();
        connection.setDoInput(true);
        connection.setRequestProperty("Authorization", "Basic " + getBase64Encoding(loginForm.getEmail(), loginForm.getPassword()));

        return connection.getResponseCode();
    }

    private String getBase64Encoding(String email, String password) {
        String raw = email + ":" + password;
        return Base64.encodeToString(raw.getBytes(), Base64.DEFAULT);
    }
}
