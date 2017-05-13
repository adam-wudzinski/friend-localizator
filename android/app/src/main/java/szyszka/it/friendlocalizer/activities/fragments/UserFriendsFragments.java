package szyszka.it.friendlocalizer.activities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Properties;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.activities.UserActivity;
import szyszka.it.friendlocalizer.activities.adapters.RequiredSearch;
import szyszka.it.friendlocalizer.server.http.FriedLocatorAPI;
import szyszka.it.friendlocalizer.server.http.tasks.SearchUsersTask;

/**
 * Created by Squier on 01.05.2017.
 */

public class UserFriendsFragments extends Fragment {

    public static final String API_SEARCH_TARGET = "search-target";

    private Context context;
    private FriedLocatorAPI api;
    private Properties apiConfig;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_view_list, container, false);

        SearchUsersTask task = new SearchUsersTask(getApiConfig(), getApi(), getMyContext());
        task.setList((ListView)v.findViewById(R.id.friendsList));
        task.execute(RequiredSearch.fromString(getArguments().getString(API_SEARCH_TARGET)));

        return v;
    }

    public static UserFriendsFragments newInstance(RequiredSearch dataSource, Context context, FriedLocatorAPI api, Properties apiConfig) {
        UserFriendsFragments f = new UserFriendsFragments();

        Bundle b = new Bundle();
        b.putString(API_SEARCH_TARGET, dataSource.toString());

        f.setMyContext(context);
        f.setArguments(b);
        f.setApi(api);
        f.setApiConfig(apiConfig);
        return f;
    }

    public Context getMyContext() {
        return context;
    }

    public void setMyContext(Context context) {
        this.context = context;
    }

    public FriedLocatorAPI getApi() {
        return api;
    }

    public void setApi(FriedLocatorAPI api) {
        this.api = api;
    }

    public Properties getApiConfig() {
        return apiConfig;
    }

    public void setApiConfig(Properties apiConfig) {
        this.apiConfig = apiConfig;
    }
}
