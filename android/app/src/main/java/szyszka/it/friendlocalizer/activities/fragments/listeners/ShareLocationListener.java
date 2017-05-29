package szyszka.it.friendlocalizer.activities.fragments.listeners;

import android.view.View;
import android.widget.ImageView;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.activities.UserActivity;
import szyszka.it.friendlocalizer.server.http.tasks.ShareLocationTask;

/**
 * Created by Squier on 29.05.2017.
 */

public class ShareLocationListener extends FragmentActionsListener {

    public static final String DONT_SHARE_LOCATION = "DELETE";
    public static final String SHARE_LOCATION = "POST";

    private int userId;
    private String requestedMethod;
    private ImageView shareLocationSwitch;

    public ShareLocationListener(UserActivity activity, int userId, String requestedMethod, ImageView shareLocationSwitch) {
        super(activity);
        this.userId = userId;
        this.requestedMethod = requestedMethod;
        this.shareLocationSwitch = shareLocationSwitch;
    }

    @Override
    public void onClick(View v) {
        replaceListener();
        ShareLocationTask shareLocationTask = new ShareLocationTask(
                getActivity().getApiConfig(), getActivity().getApi(), requestedMethod
        );
        shareLocationTask.execute(userId);
    }

    private void replaceListener() {
        if(requestedMethod.equals(DONT_SHARE_LOCATION)) {
            shareLocationSwitch.setImageResource(R.drawable.ic_share_location);
            shareLocationSwitch.setOnClickListener(
                    new ShareLocationListener(getActivity(), userId, SHARE_LOCATION, shareLocationSwitch)
            );
        } else if(requestedMethod.equals(SHARE_LOCATION)) {
            shareLocationSwitch.setImageResource(R.drawable.ic_dont_share_location);
            shareLocationSwitch.setOnClickListener(
                    new ShareLocationListener(getActivity(), userId, DONT_SHARE_LOCATION, shareLocationSwitch)
            );
        }
    }
}
