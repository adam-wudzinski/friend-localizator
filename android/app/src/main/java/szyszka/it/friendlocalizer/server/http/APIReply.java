package szyszka.it.friendlocalizer.server.http;

import szyszka.it.friendlocalizer.server.http.errors.Error;
import szyszka.it.friendlocalizer.server.users.User;

/**
 * Created by Squier on 26.04.2017.
 */

public class APIReply {

    public static final APIReply NO_REPLY = new APIReply("", Error.NO_API_REPLY, null);
    private String JSON;
    private int statusCode;
    private Class objectType;

    public APIReply(String JSON, int statusCode, Class objectType) {
        this.JSON = JSON;
        this.statusCode = statusCode;
        this.objectType = objectType;
    }

    public String getJSON() {
        return JSON;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Class getObjectType() {
        return objectType;
    }
}
