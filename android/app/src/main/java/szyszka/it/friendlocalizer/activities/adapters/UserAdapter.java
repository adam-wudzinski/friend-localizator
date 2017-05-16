package szyszka.it.friendlocalizer.activities.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.List;

import szyszka.it.friendlocalizer.server.users.UserDTO;

/**
 * Created by Squier on 13.05.2017.
 */

public interface UserAdapter extends ListAdapter {

    @Override
    View getView(int position, View convertView, ViewGroup parent);

    void clearDataSet();
    void addAllToDataSet(List<UserDTO> items);

}
