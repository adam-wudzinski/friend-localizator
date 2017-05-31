package szyszka.it.friendlocalizer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Properties;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.common.forms.LoginForm;
import szyszka.it.friendlocalizer.common.readers.PropertiesReader;
import szyszka.it.friendlocalizer.server.http.FriendLocatorAPI;
import szyszka.it.friendlocalizer.server.http.tasks.LoginTask;

import static szyszka.it.friendlocalizer.server.http.FriendLocatorAPI.API_CONFIG;


public class HelloActivity extends AppCompatActivity {

    private FriendLocatorAPI api;
    private Properties apiConfig;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        initTitle();
        initApiConfig();
        initApi();
        initActions();
    }

    private void initTitle() {
        toolbar = (Toolbar) findViewById(R.id.titleBar);
        toolbar.setTitle(R.string.app_name);
    }

    private void initApiConfig() {
        apiConfig = new PropertiesReader(getApplicationContext(), new Properties()).readMyProperties(API_CONFIG);
    }

    private void initApi() {
        api = new FriendLocatorAPI(apiConfig);
    }

    private LoginForm getLoginForm() {
        return new LoginForm(
                (EditText) findViewById(R.id.emailInput),
                (EditText) findViewById(R.id.passInput)
        );
    }

    private void initActions() {

        Button signIn = (Button) findViewById(R.id.signIn);
        Button signUp = (Button) findViewById(R.id.signUp);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginTask task = new LoginTask(api, getApplicationContext(), apiConfig);
                task.execute(getLoginForm());
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpActivity = new Intent(getApplicationContext(), SignUpActivity.class);
                signUpActivity.putExtra("api", api);
                startActivity(signUpActivity);
            }
        });
    }
}
