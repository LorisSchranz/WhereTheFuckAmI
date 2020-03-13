package ch.ls.wherethefuckami;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import ch.ls.wherethefuckami.Models.Information;

public class MainActivity extends Activity {
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    public ArrayList<Information> locations = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getActionBar().setLogo(R.drawable.logo);
        getActionBar().setDisplayUseLogoEnabled(true);
        super.onCreate(savedInstanceState);
        locations.add(0, new Information(0,0, 0));
        setContentView(R.layout.activity_main);
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
                double speed = locationResult.getLastLocation().getSpeed();
                double latitude = locationResult.getLastLocation().getLatitude();
                double longitude = locationResult.getLastLocation().getLongitude();
                Information test = new Information(longitude, latitude, speed);

                locations.set(0,test);
            }
        };
        requestLocation();
    }

    private void requestLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startService();
            } else {
                Toast.makeText(this, "Give me Location Permissions", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void loadingimg(){

        Resources res = getResources();
        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.loading_screen, null);
    }
    public void getToMap(View view) {
        startActivity(new Intent(this, MapActivity.class));
    }
}
