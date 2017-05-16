package szyszka.it.friendlocalizer.activities.fragments.listeners;

import android.view.View;

import szyszka.it.friendlocalizer.activities.UserActivity;
import szyszka.it.friendlocalizer.activities.adapters.FriendsAdapter;
import szyszka.it.friendlocalizer.activities.fragments.AllUsersFragment;
import szyszka.it.friendlocalizer.activities.fragments.FriendsFragment;


/**
 * Created by Squier on 15.05.2017.
 */

public abstract class FragmentActionsListener implements View.OnClickListener {

    private UserActivity activity;

    public FragmentActionsListener(UserActivity activity) {
        this.activity = activity;
    }

    public UserActivity.UserPagerAdapter getPagerAdapter() {
        return activity.getUserPagerAdapter();
    }

    public FriendsFragment getFriendsAdapter() {
        return activity.getFriendsFragment();
    }

    public AllUsersFragment getAllUsersFragment() {
        return activity.getAllUsersFragment();
    }

    public UserActivity getActivity() {
        return activity;
    }
}
