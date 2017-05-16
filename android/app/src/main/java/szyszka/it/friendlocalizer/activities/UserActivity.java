package szyszka.it.friendlocalizer.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Properties;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.activities.adapters.AllUsersAdapter;
import szyszka.it.friendlocalizer.activities.adapters.FriendsAdapter;
import szyszka.it.friendlocalizer.activities.fragments.AllUsersFragment;
import szyszka.it.friendlocalizer.activities.fragments.FriendsFragment;
import szyszka.it.friendlocalizer.common.readers.PropertiesReader;
import szyszka.it.friendlocalizer.server.http.FriedLocatorAPI;
import szyszka.it.friendlocalizer.server.users.UserDTO;

/**
 * Created by Rafał on 30.03.2017.
 */

public class UserActivity extends FragmentActivity {

    public static final String TAG = UserActivity.class.getSimpleName();

    private static final int MY_FRIENDS_PAGE = 0;
    private static final int SEARCH_FOR_FRIENDS_PAGE = 1;

    private FriendsFragment friendsFragment;
    private AllUsersFragment allUsersFragment;
    private UserPagerAdapter userPagerAdapter;

    private TextView userName;
    private EditText searchForFriends;
    private ViewPager pages;
    private TabLayout tabLayout;
    private ImageButton search;
    private ImageView userPicture;

    private UserDTO user;
    private Properties apiConfig;
    private FriedLocatorAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        loadConfig();
        loadBundles();

        initFragments();
        initView();
        initActions();

        fillView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadConfig() {
        apiConfig = new PropertiesReader(getApplicationContext(), new Properties()).readMyProperties(FriedLocatorAPI.API_CONFIG);
    }

    private void fillView() {
        String userFullName = user.getName() + " " + user.getSurname();
        userName.setText(userFullName);
    }

    private void initView() {
        userName = (TextView) findViewById(R.id.userName);
        searchForFriends = (EditText) findViewById(R.id.searchFriendsText);

        userPicture = (ImageView) findViewById(R.id.friendIcon);
        userPicture.setImageResource(R.mipmap.ic_launcher);

        search = (ImageButton) findViewById(R.id.searchFriendsButton);
        search.setImageResource(R.drawable.ic_search);

        pages = (ViewPager) findViewById(R.id.userPages);
        pages.setAdapter(userPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pages);

        selectPage(SEARCH_FOR_FRIENDS_PAGE);
    }

    public void initFragments() {
        Context context = getApplicationContext();

        friendsFragment = new FriendsFragment();
        friendsFragment.setAdapter(new FriendsAdapter(context, 0, new ArrayList<UserDTO>(), this));
        friendsFragment.setSearchUsersTaskArguments(apiConfig, api);

        allUsersFragment = new AllUsersFragment();
        allUsersFragment.setAdapter(new AllUsersAdapter(context, 0, new ArrayList<UserDTO>(), this));
        allUsersFragment.setSearchUsersTaskArguments(apiConfig, api);

        userPagerAdapter = new UserPagerAdapter(getSupportFragmentManager());
    }

    private void initActions() {

        searchForFriends.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reloadPagerAdapterWhenSearching();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchForFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPage(SEARCH_FOR_FRIENDS_PAGE);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadPagerAdapterWhenSearching();
            }
        });

    }

    public void reloadPageAdapter() {
        pages.setAdapter(null);
        pages.setAdapter(userPagerAdapter);
    }

    private void reloadPagerAdapterWhenSearching() {
        allUsersFragment.setSearchPhrase(searchForFriends.getText().toString());
        pages.setAdapter(null);
        pages.setAdapter(userPagerAdapter);
        selectPage(SEARCH_FOR_FRIENDS_PAGE);
    }

    private void selectPage(int position) {
        tabLayout.setScrollPosition(position, 0f, true);
        pages.setCurrentItem(position);
    }

    private void loadBundles() {
        Bundle bundle = getIntent().getExtras();
        String userJson = bundle.getString("user_data");
        user = UserDTO.fromJSON(userJson);
        api = getIntent().getParcelableExtra("api");
    }

    public UserPagerAdapter getUserPagerAdapter() {
        return userPagerAdapter;
    }

    public FriendsFragment getFriendsFragment() {
        return friendsFragment;
    }

    public AllUsersFragment getAllUsersFragment() {
        return allUsersFragment;
    }

    public Properties getApiConfig() {
        return apiConfig;
    }

    public FriedLocatorAPI getApi() {
        return api;
    }

    public class UserPagerAdapter extends FragmentPagerAdapter {

        private final String[] tabTitles = getResources().getStringArray(R.array.tabsTitles);

        public UserPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case MY_FRIENDS_PAGE: {
                    return friendsFragment;
                }
                case SEARCH_FOR_FRIENDS_PAGE: {
                    return allUsersFragment;
                }
                default: {
                    return friendsFragment;
                }
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

}