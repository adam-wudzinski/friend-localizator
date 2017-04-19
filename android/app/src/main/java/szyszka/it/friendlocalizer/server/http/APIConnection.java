package szyszka.it.friendlocalizer.server.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import szyszka.it.friendlocalizer.server.users.User;

import static szyszka.it.friendlocalizer.server.users.User.getJSON;

/**
 * Created by Squier on 19.04.2017.
 */

public class APIConnection {

    public static final String API_LINK = "";
    public static final String REG_LINK = "/auth/register";

    private String apiEndpoint;

    public APIConnection() {
    }

    public APIConnection(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public void registerUser(User user, URL regURL) {
        try {
            String userJson = getJSON(user);
            HttpURLConnection connection = (HttpURLConnection) regURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(userJson.length()));
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.getOutputStream().write(userJson.getBytes());
            //TODO odczyt odpowiedzi
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
