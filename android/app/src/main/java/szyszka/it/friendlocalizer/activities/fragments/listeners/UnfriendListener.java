package szyszka.it.friendlocalizer.activities.fragments.listeners;

import android.view.View;

import szyszka.it.friendlocalizer.activities.UserActivity;
import szyszka.it.friendlocalizer.server.http.tasks.ShareLocationTask;
import szyszka.it.friendlocalizer.server.http.tasks.UnfriendTask;

/**
 * Created by Squier on 16.05.2017.
 */

public class UnfriendListener extends FragmentActionsListener {

    private int userId;

    public UnfriendListener(UserActivity activity, int userId) {
        super(activity);
        this.userId = userId;
    }

    @Override
    public void onClick(View v) {
        UserActivity activity = getActivity();

        ShareLocationTask shareLocationTask = new ShareLocationTask(
                activity.getApiConfig(),
                activity.getApi(),
                ShareLocationListener.DONT_SHARE_LOCATION
        );
        shareLocationTask.execute(userId);

        UnfriendTask addAsFriendTask = new UnfriendTask(
                activity.getApiConfig(),
                activity.getApi(),
                activity
        );
        addAsFriendTask.execute(userId);
    }

}
