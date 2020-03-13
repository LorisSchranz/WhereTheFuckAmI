package ch.ls.wherethefuckami;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import ch.ls.wherethefuckami.Models.CurrentLocation;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    private ArrayList<CurrentLocation> locations = new ArrayList<>();
    boolean firstTime = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locations.add(new CurrentLocation(0,0));
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //request Location
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                // request Permissions
                startService();
            }
        } else {
            // show Location
            startService();
        }
    }

    void startService() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                double latitude = locationResult.getLastLocation().getLatitude();
                double longitude = locationResult.getLastLocation().getLongitude();
                CurrentLocation test = new CurrentLocation(longitude, latitude);
                locations.set(0, test);
                if(firstTime){
                    setLocation();
                }
            }
        };
        requestLocation();
    }

    private void requestLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(200);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private void setLocation(){
        /*
        Marker for Search function

        LatLng currentLocation = new LatLng(locations.get(0).getLat(), locations.get(0).getLong());
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker at your position"));
        Log.d("just a test", String.valueOf(mMap.getMyLocation()));
         */

        LatLng currentLocation = new LatLng(locations.get(0).getLat(), locations.get(0).getLong());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        firstTime = false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng horgen = new LatLng(47.260345, 8.595858);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(horgen));
        mMap.setMyLocationEnabled(true);
    }
    public void extendInformation(View view) {
        // Gets linearlayout
        TextView layout = findViewById(R.id.information);
        // Gets the layout params that will allow you to resize the layout
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        // Changes the height and width to the specified *pixels*

        TextView view1 = findViewById(R.id.info1);
        TextView view2 = findViewById(R.id.info2);
        TextView view3 = findViewById(R.id.info3);
        TextView view4 = findViewById(R.id.info4);
        TextView view5 = findViewById(R.id.info5);
        TextView view6 = findViewById(R.id.info6);
        if(view1.equals(View.INVISIBLE)){
            params.height = 300;
            layout.setLayoutParams(params);

            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.VISIBLE);
            view3.setVisibility(View.VISIBLE);
            view4.setVisibility(View.VISIBLE);
            view5.setVisibility(View.VISIBLE);
            view6.setVisibility(View.VISIBLE);
        }else{
            params.height = 20;
            layout.setLayoutParams(params);

            view1.setVisibility(View.INVISIBLE);
            view2.setVisibility(View.INVISIBLE);
            view3.setVisibility(View.INVISIBLE);
            view4.setVisibility(View.INVISIBLE);
            view5.setVisibility(View.INVISIBLE);
            view6.setVisibility(View.INVISIBLE);
        }

    }
}
