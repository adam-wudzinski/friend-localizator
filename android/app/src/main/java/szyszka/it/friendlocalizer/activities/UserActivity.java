package szyszka.it.friendlocalizer.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Properties;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.activities.adapters.RequiredSearch;
import szyszka.it.friendlocalizer.activities.fragments.UserFriendsFragments;
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

        initView();
        loadConfig();
        loadBundles();
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

        userPicture = (ImageView) findViewById(R.id.friendIcon);
        userPicture.setImageResource(R.mipmap.ic_launcher);

        search = (ImageButton) findViewById(R.id.searchFriendsButton);
        search.setImageResource(R.drawable.ic_search);

        pages = (ViewPager) findViewById(R.id.userPages);
        pages.setAdapter(new UserPagerAdapter(getSupportFragmentManager()));
        pages.setCurrentItem(0);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pages);
    }

    private void loadBundles() {
        Bundle bundle = getIntent().getExtras();
        String userJson = bundle.getString("user_data");
        user = UserDTO.fromJSON(userJson);
        api = getIntent().getParcelableExtra("api");
    }

    private class UserPagerAdapter extends FragmentPagerAdapter {

        private String[] tabTitles = getResources().getStringArray(R.array.tabsTitles);

        public UserPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case MY_FRIENDS_PAGE: {
                    return UserFriendsFragments.newInstance(
                            RequiredSearch.FRIENDS,
                            getApplicationContext(),
                            api,
                            apiConfig
                    );
                }
                case SEARCH_FOR_FRIENDS_PAGE: {
                    return UserFriendsFragments.newInstance(
                            RequiredSearch.USERS,
                            getApplicationContext(),
                            api,
                            apiConfig
                    );
                }
                default: {
                    return UserFriendsFragments.newInstance(
                            RequiredSearch.FRIENDS,
                            getApplicationContext(),
                            api,
                            apiConfig
                    );
                }
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

}
