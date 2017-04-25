package szyszka.it.friendlocalizer.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.UserActivity;
import szyszka.it.friendlocalizer.common.forms.LoginForm;
import szyszka.it.friendlocalizer.server.http.APIConnection;

import static java.net.HttpURLConnection.HTTP_OK;


public class HelloActivity extends AppCompatActivity {

    private APIConnection api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        initActions();
    }

    private void initApi() {
        api = new APIConnection();
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

                new AsyncTask<LoginForm, Void, Integer>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        initApi();
                    }

                    @Override
                    protected Integer doInBackground(LoginForm... params) {
                        int response = -1;
                        try {
                            response = api.loginUser(params[0], new URL(APIConnection.API_URL + "/api"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return response;
                    }

                    @Override
                    protected void onPostExecute(Integer response) {
                        super.onPostExecute(response);
                        Log.i("SIGN_IN", String.valueOf(response));
                        if(response == HTTP_OK) {
                            Intent signInActivity = new Intent(getApplicationContext(), UserActivity.class);
                            startActivityForResult(signInActivity, 100);
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong :/", Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute(getLoginForm());
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpActivity = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUpActivity);
            }
        });
    }
}
