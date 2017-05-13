package szyszka.it.friendlocalizer.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.server.users.UserDTO;

/**
 * Created by Squier on 26.04.2017.
 */

public class AllUsersAdapter extends ArrayAdapter<UserDTO> implements UserAdapter {

    public static final String TAG = AllUsersAdapter.class.getSimpleName();

    public AllUsersAdapter(Context context, int resource, List<UserDTO> users) {
        super(context, resource, users);
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
                rowView.findViewById(R.id.locateFriend)
        );

        UserDTO user = getItem(position);

        handle.friendIcon.setImageResource(R.mipmap.ic_user_default);
        handle.friendName.setText(user.getName() + " " + user.getSurname());
        handle.friendEmail.setText(user.getEmail());
        handle.locateFriend.setImageResource(R.drawable.ic_inactive_locate_friend);
        handle.addRemoveFriend.setImageResource(R.drawable.ic_add_as_friend);

        return rowView;
    }

    public static class ViewHandle {
        public ImageView friendIcon;
        public TextView friendName;
        public TextView friendEmail;
        public ImageView locateFriend;
        public ImageView addRemoveFriend;

        public ViewHandle(View friendIcon, View friendName, View friendEmail, View addRemoveFriend, View locateFriend) {
            this.friendIcon = (ImageView) friendIcon;
            this.friendName = (TextView) friendName;
            this.friendEmail = (TextView) friendEmail;
            this.addRemoveFriend = (ImageView) addRemoveFriend;
            this.locateFriend = (ImageView) locateFriend;
        }
    }
}
