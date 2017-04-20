package szyszka.it.friendlocalizer.common.forms;

import android.widget.EditText;

/**
 * Created by Squier on 20.04.2017.
 */

public class LoginForm {

    private EditText email;
    private EditText password;

    public LoginForm(EditText email, EditText password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email.getText().toString();
    }

    public void setEmail(EditText email) {
        this.email = email;
    }

    public String getPassword() {
        return password.getText().toString();
    }

    public void setPassword(EditText password) {
        this.password = password;
    }
}
