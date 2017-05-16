package szyszka.it.friendlocalizer.activities.fragments.listeners;

import android.view.View;

import szyszka.it.friendlocalizer.activities.UserActivity;
import szyszka.it.friendlocalizer.server.http.tasks.UnfriendTask;

/**
 * Created by Squier on 16.05.2017.
 */

public class UnfriendListener extends FragmentActionsListener {

    private int id;

    public UnfriendListener(UserActivity activity, int id) {
        super(activity);
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        UserActivity activity = getActivity();
        UnfriendTask addAsFriendTask = new UnfriendTask(
                activity.getApiConfig(),
                activity.getApi(),
                activity
        );
        addAsFriendTask.execute(id);
    }

}
