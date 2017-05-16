package szyszka.it.friendlocalizer.activities.fragments.listeners;

import android.view.View;

import szyszka.it.friendlocalizer.activities.UserActivity;
import szyszka.it.friendlocalizer.server.http.tasks.AddAsFriendTask;

/**
 * Created by Squier on 15.05.2017.
 */

public class AddAsFriendListener extends FragmentActionsListener {

    private int id;

    public AddAsFriendListener(UserActivity activity, int id) {
        super(activity);
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        UserActivity activity = getActivity();
        AddAsFriendTask addAsFriendTask = new AddAsFriendTask(
                activity.getApiConfig(),
                activity.getApi(),
                activity
        );
        addAsFriendTask.execute(id);
    }
}
