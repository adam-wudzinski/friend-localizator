package szyszka.it.friendlocalizer.registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.MalformedURLException;
import java.net.URL;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.common.forms.SignUpForm;
import szyszka.it.friendlocalizer.server.http.APIConnection;
import szyszka.it.friendlocalizer.server.users.User;

import static szyszka.it.friendlocalizer.server.http.APIConnection.*;

/**
 * Created by Rafał on 30.03.2017.
 */

public class SignUpActivity extends AppCompatActivity {

    private User newUser;
    private SignUpForm signUpForm;
    private APIConnection api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initForm();
        initButtons();
        initAPI();
    }

    private void initAPI() {
        api = new APIConnection();
    }

    private void initForm() {
        signUpForm = new SignUpForm(
                (EditText)findViewById(R.id.userName),
                (EditText)findViewById(R.id.userSurname),
                (EditText)findViewById(R.id.userEmail),
                (EditText)findViewById(R.id.password),
                (EditText)findViewById(R.id.repeatedPass)
        );
    }

    private void initButtons() {
        final Button signUp = (Button) findViewById(R.id.confirmSignUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO walidacja
                newUser = signUpForm.getUserFromForm();
                try {
                    api.registerUser(newUser, new URL(API_LINK + REG_LINK));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
