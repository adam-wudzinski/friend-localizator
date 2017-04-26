package szyszka.it.friendlocalizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Properties;

import szyszka.it.friendlocalizer.common.readers.PropertiesReader;
import szyszka.it.friendlocalizer.server.http.FriedLocatorAPI;
import szyszka.it.friendlocalizer.server.http.tasks.SearchUsersTask;
import szyszka.it.friendlocalizer.server.users.UserDTO;

/**
 * Created by Rafał on 30.03.2017.
 */

public class UserActivity extends AppCompatActivity {

    public static final String TAG = UserActivity.class.getSimpleName();

    private UserDTO user;
    private TextView userName;
    private ListView users;
    private Properties apiConfig;
    private FriedLocatorAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initView();
        loadConfig();
        loadBundles();
        fillView();
        loadUsers();
    }

    private void loadConfig() {
        apiConfig = new PropertiesReader(getApplicationContext(), new Properties()).readMyProperties(FriedLocatorAPI.API_CONFIG);
    }

    private void loadUsers() {
        SearchUsersTask task = new SearchUsersTask(apiConfig, api, getApplicationContext());
        task.setList(users);
        task.execute();
    }

    private void fillView() {
        String userFullName = user.getName() + " " + user.getSurname();
        userName.setText(userFullName);
    }

    private void initView() {
        userName = (TextView) findViewById(R.id.userName);
        users = (ListView) findViewById(R.id.friendList);
    }

    private void loadBundles() {
        Bundle bundle = getIntent().getExtras();
        String userJson = bundle.getString("user_data");
        user = UserDTO.fromJSON(userJson);
        api = getIntent().getParcelableExtra("api");
    }
}
