package szyszka.it.friendlocalizer.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Properties;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.activities.adapters.AllUsersAdapter;
import szyszka.it.friendlocalizer.activities.adapters.RequiredSearch;
import szyszka.it.friendlocalizer.server.http.FriendLocatorAPI;
import szyszka.it.friendlocalizer.server.http.tasks.SearchUsersTask;

import static szyszka.it.friendlocalizer.activities.adapters.RequiredSearch.Requirement.*;

/**
 * Created by Squier on 15.05.2017.
 */

public class AllUsersFragment extends Fragment {

    private AllUsersAdapter adapter;
    private Properties apiConfig;
    private FriendLocatorAPI api;
    private String searchPhrase = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_view_list, container, false);

        SearchUsersTask searchUsersTask = new SearchUsersTask(apiConfig, api);
        searchUsersTask.setList((ListView) v.findViewById(R.id.friendsList));
        searchUsersTask.setAdapter(adapter);
        searchUsersTask.setListAdapter();
        searchUsersTask.execute(new RequiredSearch(searchPhrase, USERS));

        return v;

    }

    public void setAdapter(AllUsersAdapter adapter) {
        this.adapter = adapter;
    }

    public void setSearchUsersTaskArguments(Properties apiConfig, FriendLocatorAPI api) {
        this.apiConfig = apiConfig;
        this.api = api;
    }

    public void setSearchPhrase(String searchPhrase) {
        this.searchPhrase = searchPhrase;
    }
}
