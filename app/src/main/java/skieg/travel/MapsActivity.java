package skieg.travel;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
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
import android.Manifest.permission;

import java.security.Permission;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMaps2Binding binding;
   // private Location locationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
       // locationData = new Location();

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

        // Add a marker in BCIT and move the camera
        LatLng bcit = new LatLng(49.25010954461797, -123.00275621174804);
        mMap.addMarker(new MarkerOptions().position(bcit).title("BCIT"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bcit));


//        mMap.setOnMyLocationButtonClickListener(locationData);
//        mMap.setOnMyLocationClickListener(locationData);

    }

//    private  class Location extends AppCompatActivity
//            implements
//            OnMyLocationButtonClickListener,
//            OnMyLocationClickListener,
//            OnMapReadyCallback,
//            ActivityCompat.OnRequestPermissionsResultCallback {
//
//        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
//        private boolean permissionDenied = false;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
////            SupportMapFragment mapFragment =
////                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
////            mapFragment.getMapAsync(this);
//        }
//
//        @Override
//        public boolean onMyLocationButtonClick() {
//            Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
//            // Return false so that we don't consume the event and the default behavior still occurs
//            // (the camera animates to the user's current position).
//            return false;
//        }
//
//
//        public void onMyLocationClick(@NonNull Location location) {
//            Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onMyLocationClick(@NonNull android.location.Location location) {
//            Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onMapReady(@NonNull GoogleMap googleMap) {
////            mMap = googleMap;
////            mMap.setOnMyLocationButtonClickListener(this);
////            mMap.setOnMyLocationClickListener(this);
//            enableMyLocation();
//        }
//
//        /**
//         * Enables the My Location layer if the fine location permission has been granted.
//         */
//        @SuppressLint("MissingPermission")
//        private void enableMyLocation() {
//            // 1. Check if permissions are granted, if so, enable the my location layer
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED
//                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//                mMap.setMyLocationEnabled(true);
//                return;
//            }
//            // Manifest.permission.
//            // 2. Otherwise, request location permissions from the user.
//           PermissionUtils.requestLocationPermissions(this, LOCATION_PERMISSION_REQUEST_CODE, true);
//        }
//
//        @Override
//        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                               @NonNull int[] grantResults) {
//            if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//                return;
//            }
//
//            if (PermissionUtils.isPermissionGranted(permissions, grantResults,
//                    Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtils
//                    .isPermissionGranted(permissions, grantResults,
//                            Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                // Enable the my location layer if the permission has been granted.
//                enableMyLocation();
//            } else {
//                // Permission was denied. Display an error message
//                // Display the missing permission error dialog when the fragments resume.
//                permissionDenied = true;
//            }
//        }
//    }

}