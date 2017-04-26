package szyszka.it.friendlocalizer.common.forms;

import android.widget.EditText;

import szyszka.it.friendlocalizer.server.users.User;

/**
 * Created by Squier on 19.04.2017.
 */

public class RegisterForm {

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText pass;
    private EditText rPass;

    public RegisterForm(EditText name, EditText surname, EditText email, EditText pass, EditText rPass) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pass = pass;
        this.rPass = rPass;
    }

    public szyszka.it.friendlocalizer.server.users.User getUserFromForm() {
        return new szyszka.it.friendlocalizer.server.users.User(
                name.getText().toString(),
                surname.getText().toString(),
                email.getText().toString(),
                pass.getText().toString()
        );
    }
}
