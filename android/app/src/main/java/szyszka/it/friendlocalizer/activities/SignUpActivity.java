package szyszka.it.friendlocalizer.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

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
            public void onClick(View v) {//TODO walidacja
                newUser = signUpForm.getUserFromForm();
                new AsyncTask<Void, Void, Integer>() {
                    @Override
                    protected Integer doInBackground(Void... params) {
                        int reply = -1;
                        try {
                            reply = api.registerUser(newUser, new URL(API_URL + REG_LINK));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return reply;
                    }

                    @Override
                    protected void onPostExecute(Integer result) {
                        if(result == HttpsURLConnection.HTTP_OK) {
                            Toast.makeText(getApplicationContext(), "You have successfully registered.", Toast.LENGTH_LONG).show();
                            Log.i("API", "Reg succeed " + result);
                        } else {
                            Toast.makeText(getApplicationContext(), "Errors occurred during registration.", Toast.LENGTH_LONG).show();
                            Log.i("API", "Reg failed " + result);
                        }
                    }
                }.execute();
            }
        });
    }
}
