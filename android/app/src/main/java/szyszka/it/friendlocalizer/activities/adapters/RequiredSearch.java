package szyszka.it.friendlocalizer.activities.adapters;

/**
 * Created by Squier on 13.05.2017.
 */

public enum RequiredSearch {
    FRIENDS, USERS;

    @Override
    public String toString() {
        switch (this) {
            case FRIENDS:   return "friends";
            case USERS:     return "users";
            default:        return "";
        }
    }

    public static RequiredSearch fromString(String requiredSearch) {
        switch (requiredSearch) {
            case "friends": return FRIENDS;
            case "users":   return USERS;
            default:        return null;
        }
    }
}
