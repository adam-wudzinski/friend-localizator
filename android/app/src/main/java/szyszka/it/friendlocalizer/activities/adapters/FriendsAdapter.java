package szyszka.it.friendlocalizer.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.activities.UserActivity;
import szyszka.it.friendlocalizer.activities.fragments.listeners.ShareLocationListener;
import szyszka.it.friendlocalizer.activities.fragments.listeners.UnfriendListener;
import szyszka.it.friendlocalizer.server.users.UserDTO;

import static szyszka.it.friendlocalizer.activities.adapters.AllUsersAdapter.*;

/**
 * Created by Squier on 13.05.2017.
 */

public class FriendsAdapter extends ArrayAdapter<UserDTO> implements UserAdapter {

    private UserActivity activity;

    public FriendsAdapter(Context context, int resource, List<UserDTO> objects, UserActivity activity) {
        super(context, resource, objects);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        if(rowView == null) {
            rowView = LayoutInflater.from(getContext()).inflate(R.layout.user_view_user_row, parent, false);
        }

        final ViewHandle handle = new ViewHandle(
                rowView.findViewById(R.id.friendIcon),
                rowView.findViewById(R.id.friendName),
                rowView.findViewById(R.id.friendEmail),
                rowView.findViewById(R.id.addRemoveFriend),
                rowView.findViewById(R.id.allowLocatingMe)
        );

        final UserDTO user = getItem(position);

        handle.friendIcon.setImageResource(R.mipmap.ic_user_default);

        handle.friendName.setText(user.getName() + " " + user.getSurname());
        handle.friendEmail.setText(user.getEmail());

        handle.addRemoveFriend.setImageResource(R.drawable.ic_minus);
        handle.allowLocatingMe.setImageResource(R.drawable.ic_share_location);

        initActions(handle, user);

        return rowView;
    }

    private void initActions(ViewHandle handle, UserDTO user) {
        handle.addRemoveFriend.setOnClickListener(new UnfriendListener(activity, user.getId()));
        handle.allowLocatingMe.setOnClickListener(
                new ShareLocationListener(
                        activity,
                        user.getId(),
                        ShareLocationListener.SHARE_LOCATION,
                        handle.allowLocatingMe
                )
        );
    }

    @Override
    public void clearDataSet() {
        clear();
    }

    @Override
    public void addAllToDataSet(List<UserDTO> items) {
        addAll(items);
    }

}
