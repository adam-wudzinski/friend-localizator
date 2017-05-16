package szyszka.it.friendlocalizer.activities.adapters;

/**
 * Created by Squier on 13.05.2017.
 */

public class RequiredSearch {

    public enum Requirement {
        FRIENDS, USERS;

        @Override
        public String toString() {
            switch (this) {
                case FRIENDS:
                    return "friends";
                case USERS:
                    return "users";
                default:
                    return "";
            }
        }

        public static Requirement fromString(String requiredSearch) {
            switch (requiredSearch) {
                case "friends":
                    return FRIENDS;
                case "users":
                    return USERS;
                default:
                    return null;
            }
        }

    }

    private String searchPhrase;
    private Requirement requirement;

    public RequiredSearch(String searchPhrase, Requirement requirement) {
        this.searchPhrase = searchPhrase;
        this.requirement = requirement;
    }

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public Requirement getRequirement() {
        return requirement;
    }
}
