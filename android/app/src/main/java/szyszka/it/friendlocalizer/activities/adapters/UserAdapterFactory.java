package szyszka.it.friendlocalizer.activities.adapters;

import android.content.Context;

import java.util.List;

import szyszka.it.friendlocalizer.server.users.UserDTO;

/**
 * Created by Squier on 13.05.2017.
 */

public class UserAdapterFactory {

    public static UserAdapter getUserAdapter(RequiredSearch requiredInstance, Context context, List<UserDTO> users) throws IllegalRequirementException {
        switch (requiredInstance) {
            case FRIENDS: {
                return new UserFriendsAdapter(context, 0, users);
            }
            case USERS: {
                return new AllUsersAdapter(context, 0, users);
            }
            default: {
                throw new IllegalRequirementException(IllegalRequirementException.MESSAGE +
                        "\n Requirement: " + requiredInstance.toString());
            }
        }
    }

}
