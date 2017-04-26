package szyszka.it.friendlocalizer.server.users;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Created by Squier on 26.04.2017.
 */

public class UserDTO extends User {

    public static final String TAG = UserDTO.class.getSimpleName();

    public int id;

    public UserDTO() {
    }

    public UserDTO(String name, String surname, String email, String password, int id) {
        super(name, surname, email, password);
        this.id = id;
    }

    public UserDTO(String name, String surname, String email, int id) {
        super(name, surname, email, "");
        this.id = id;
    }

    public static List<UserDTO> arrayFromJSON(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, new TypeReference<List<UserDTO>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserDTO fromJSON(String json) {
        ObjectMapper mapper = new ObjectMapper();
        UserDTO user = null;
        try {
            user = mapper.readValue(json, UserDTO.class);
        } catch (IOException e) {
            Log.e(TAG, "Failed to create object from JSON string\n" + e.getLocalizedMessage());
        }
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
