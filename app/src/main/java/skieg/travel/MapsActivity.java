package skieg.travel;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import skieg.travel.Utility.PermissionUtils;
import skieg.travel.databinding.ActivityMaps2Binding;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.widget.Toast;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    // Location update time values
    int LOCATION_REFRESH_TIME = 15000; // 15 seconds to update
    int LOCATION_REFRESH_DISTANCE = 500;

    // Instance variables for map, binding maps, location, and location manager object
    private GoogleMap mMap;
    private ActivityMaps2Binding binding;
    private Location locationData;
    private LocationManager locationManager;

    /**
     * Gets the service name of a class.
     * @param serviceClass: Class
     * @return string
     */
    @Override
    public String getSystemServiceName(Class<?> serviceClass) {
        return super.getSystemServiceName(serviceClass);
    }

    /**
     * Get system's service.
     * @param name: string
     * @return object with service
     */
    @Override
    public Object getSystemService(@NonNull String name) {
        return super.getSystemService(name);
    }

    /**
     * When map object is created.
     * @param savedInstanceState: bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize instance variables
        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
        locationData = new Location();

        // Set the content view to the binder
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        LatLng bcit = new LatLng(49.25010954461797, -123.00275621174804);
        mMap.addMarker(new MarkerOptions().position(bcit).title("BCIT"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bcit));

        mMap.setOnMyLocationButtonClickListener(locationData);
        mMap.setOnMyLocationClickListener(locationData);

    }

    /**
     * Private inner class to get location attributes.
     */
    private class Location extends AppCompatActivity
            implements
            OnMyLocationButtonClickListener,
            OnMyLocationClickListener,
            OnMapReadyCallback,
            ActivityCompat.OnRequestPermissionsResultCallback {

        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
        private boolean permissionDenied = false;

        /**
         * When Location object is created.
         * @param savedInstanceState
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        /**
         * When location button is clicked.
         * Camera animates to the user's current position.
         * @return boolean, false to keep default behaviour of the event
         */
        @Override
        public boolean onMyLocationButtonClick() {
            Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
            return false;
        }

        /**
         * When location button is clicked but method has no return type.
         * @param location: Location
         */
        @Override
        public void onMyLocationClick(@NonNull android.location.Location location) {
            Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
        }

        /**
         * When map is created and ready to be used.
         * @param googleMap: GoogleMap
         */
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mMap = googleMap;
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);
            enableMyLocation();
        }

        /**
         * Enables the My Location layer if the fine location permission has been granted.
         */
        @SuppressLint("MissingPermission")
        private void enableMyLocation() {
            // 1. Check if permissions are granted, if so, enable the my location layer
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                return;
            }
            // 2. Otherwise, request location permissions from the user.
           PermissionUtils.requestLocationPermissions(this, LOCATION_PERMISSION_REQUEST_CODE, true);
        }

        /**
         * Check if user have given permission to use locations.
         * @param requestCode: integer
         * @param permissions: String array
         * @param grantResults: integer array
         */
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            // If request code doesn't match
            if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return;
            }

            // If user has given permission
            if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtils
                    .isPermissionGranted(permissions, grantResults,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Enable the my location layer if the permission has been granted.
                enableMyLocation();
            } else {
                // Permission was denied. Display an error message
                // Display the missing permission error dialog when the fragments resume.
                permissionDenied = true;
            }
        }
    }

}
