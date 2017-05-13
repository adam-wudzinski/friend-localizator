package szyszka.it.friendlocalizer.activities.adapters;

/**
 * Created by Squier on 13.05.2017.
 */

public class IllegalRequirementException extends Exception {

    public static final String MESSAGE = "No adapter instance found for given requirement.";

    public IllegalRequirementException(String message) {
        super(message);
    }

}
