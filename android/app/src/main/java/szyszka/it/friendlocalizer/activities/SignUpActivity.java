package szyszka.it.friendlocalizer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.common.forms.RegisterForm;
import szyszka.it.friendlocalizer.common.readers.PropertiesReader;
import szyszka.it.friendlocalizer.server.http.FriedLocatorAPI;
import szyszka.it.friendlocalizer.server.http.tasks.RegisterTask;
import szyszka.it.friendlocalizer.server.users.User;

import static szyszka.it.friendlocalizer.server.http.FriedLocatorAPI.API_CONFIG;

/**
 * Created by Rafał on 30.03.2017.
 */

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = SignUpActivity.class.getSimpleName();

    private User newUser;
    private RegisterForm registerForm;
    private FriedLocatorAPI api;
    private Properties apiConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initForm();
        initButtons();
        initAPI();
    }

    private void initAPI() {
        apiConfig = new PropertiesReader(getApplicationContext(), new Properties()).readMyProperties(API_CONFIG);
        api = getIntent().getParcelableExtra("api");
        Log.i(TAG, "Initialized API");
    }

    private void initForm() {
        registerForm = new RegisterForm(
                (EditText)findViewById(R.id.userName),
                (EditText)findViewById(R.id.userSurname),
                (EditText)findViewById(R.id.userEmail),
                (EditText)findViewById(R.id.password),
                (EditText)findViewById(R.id.repeatedPass)
        );
        Log.i(TAG, "Initialized form");
    }

    private void initButtons() {
        final Button signUp = (Button) findViewById(R.id.confirmSignUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//TODO walidacja
                newUser = registerForm.getUserFromForm();
                RegisterTask task = new RegisterTask(api, getApplicationContext(), apiConfig);
                task.execute(newUser);
            }
        });
    }
}
