package szyszka.it.friendlocalizer.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Properties;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.activities.adapters.FriendsAdapter;
import szyszka.it.friendlocalizer.activities.adapters.RequiredSearch;
import szyszka.it.friendlocalizer.server.http.FriendLocatorAPI;
import szyszka.it.friendlocalizer.server.http.tasks.SearchUsersTask;

import static szyszka.it.friendlocalizer.activities.adapters.RequiredSearch.Requirement.*;

/**
 * Created by Squier on 15.05.2017.
 */

public class FriendsFragment extends Fragment {

    private FriendsAdapter adapter;
    private Properties apiConfig;
    private FriendLocatorAPI api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_view_list, container, false);

        SearchUsersTask searchUsersTask = new SearchUsersTask(apiConfig, api);
        searchUsersTask.setList((ListView) v.findViewById(R.id.friendsList));
        searchUsersTask.setAdapter(adapter);
        searchUsersTask.setListAdapter();
        searchUsersTask.execute(new RequiredSearch(null, FRIENDS));

        return v;
    }

    public FriendsAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(FriendsAdapter adapter) {
        this.adapter = adapter;
    }

    public void setSearchUsersTaskArguments(Properties apiConfig, FriendLocatorAPI api) {
        this.apiConfig = apiConfig;
        this.api = api;
    }

}
