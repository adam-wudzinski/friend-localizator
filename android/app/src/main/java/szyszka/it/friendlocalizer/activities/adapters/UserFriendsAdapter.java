package szyszka.it.friendlocalizer.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.server.users.UserDTO;

import static szyszka.it.friendlocalizer.activities.adapters.AllUsersAdapter.*;

/**
 * Created by Squier on 13.05.2017.
 */

public class UserFriendsAdapter extends ArrayAdapter<UserDTO> implements UserAdapter {

    public UserFriendsAdapter(Context context, int resource, List<UserDTO> objects) {
        super(context, resource, objects);
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

        final UserDTO user = getItem(position);

        handle.friendIcon.setImageResource(R.mipmap.ic_user_default);

        handle.friendName.setText(user.getName() + " " + user.getSurname());
        handle.friendEmail.setText(user.getEmail());

        handle.addRemoveFriend.setImageResource(R.drawable.ic_minus);
        handle.locateFriend.setImageResource(R.drawable.ic_active_locate_friend);

        return rowView;
    }

}
