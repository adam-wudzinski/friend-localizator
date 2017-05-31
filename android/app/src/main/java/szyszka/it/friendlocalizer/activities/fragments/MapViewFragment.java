package szyszka.it.friendlocalizer.activities.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import szyszka.it.friendlocalizer.R;
import szyszka.it.friendlocalizer.activities.UserActivity;
import szyszka.it.friendlocalizer.location.Locations;
import szyszka.it.friendlocalizer.location.MyLocation;
import szyszka.it.friendlocalizer.server.http.tasks.LocateMyFriends;
import szyszka.it.friendlocalizer.server.http.tasks.ProvideMyLocation;
import szyszka.it.friendlocalizer.server.users.LocalizedUser;

/**
 * Created by Squier on 18.05.2017.
 */

public class MapViewFragment extends Fragment {

    public static final String TAG = MapViewFragment.class.getSimpleName();

    public static final int REQUEST_PERMISSIONS_CODE = 1301;

    private LocationManager locationManager;
    private MyLocationListener myLocationListener = null;
    private MapView mapView;
    private GoogleMap map;
    private Locations locations = new Locations(new MyLocation(), new ArrayList<LocalizedUser>(), new ArrayList<Marker>());
    private UserActivity userActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);

        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                locations.setMap(map);
                locateMe(map);
            }
        });

        return rootView;
    }

    public void stopLocationUpdates() {
        locationManager.removeUpdates(myLocationListener);
    }

    public void setUserActivity(UserActivity userActivity) {
        this.userActivity = userActivity;
    }

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    private void locateMe(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_PERMISSIONS_CODE);
            return;
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, getListener());
        }
    }

    @NonNull
    private MyLocationListener getListener() {
        if(myLocationListener == null) {
            myLocationListener = new MyLocationListener();
        }
        return myLocationListener;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, getListener());
                }
                break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private class MyLocationListener implements LocationListener {

        public final String TAG = MyLocationListener.class.getSimpleName();
        private MyLocation userLocation = new MyLocation();
        private boolean zoomed = false;

        @Override
        public void onLocationChanged(Location location) {

            userLocation.setLatitude(location.getLatitude());
            userLocation.setLongitude(location.getLongitude());
            locations.setUserLocation(userLocation);

            Log.i(TAG, "Latitude: " + userLocation.getLatitude() + " Longitude: " + userLocation.getLongitude());

            if(!zoomed) {
                map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                                new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 14.0f
                        )
                );
                zoomed = true;
            }

            LocateMyFriends locateMyFriends = new LocateMyFriends(
                    userActivity.getApiConfig(), userActivity.getApi(), locations
            );

            locateMyFriends.execute();

            ProvideMyLocation provideMyLocation = new ProvideMyLocation(
                    userActivity.getApiConfig(), userActivity.getApi()
            );

            provideMyLocation.execute(locations.getUserLocation());

        }

        @Override
        public void onProviderDisabled(String s) {
            Intent settings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settings);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }
    }
}
