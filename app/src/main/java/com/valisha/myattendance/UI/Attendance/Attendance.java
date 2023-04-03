package com.valisha.myattendance.UI.Attendance;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.valisha.myattendance.R;
import com.valisha.myattendance.UI.Dashboard.Dashboard;
import com.valisha.myattendance.apps.IApp;
import com.valisha.myattendance.model.Logs;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Attendance extends FragmentActivity implements OnMapReadyCallback {

    Location currentLocation;
    LocationManager locationManager;
    String provider;
    FusedLocationProviderClient fusedLocationProviderClient;
    private TextView tvAddress,tvDate,tvTime;
    private String date,time,address;
    private static final int PERMISSION_REQUEST_CODE = 200;

    private IApp app;
    private GoogleMap map;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        if (!checkPermission()) {
            requestPermission();
        }

        app = ((IApp) getApplicationContext());
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
         fetchLocation();



        tvAddress = findViewById(R.id.tv_address);
        tvDate = findViewById(R.id.tv_date);
        tvTime = findViewById(R.id.tv_time);

        Log.e("TAG", "onCreate: ");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        date = dtf.format(now);
        tvDate.setText(" "+dtf.format(now));

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now2 = LocalDateTime.now();
        time = dtf2.format(now2);
        tvTime.setText(" "+dtf2.format(now2));

        if(map!=null)
            onMapReady(map);


    }

    private void fetchLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                   // Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(Attendance.this);
                }
            }
        });
    }





    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        map = googleMap;

        Log.e(  "onMapReady: ",""+currentLocation.getLatitude() );
        Log.e( "onMapReady: ",""+currentLocation.getLongitude() );

        address(currentLocation.getLatitude(),currentLocation.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        googleMap.addMarker(markerOptions);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted) {
                        Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION) && shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION) ) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Attendance.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    public String address(double LATITUDE, double LONGITUDE)
    {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());


            addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5


            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();


            Log.e("TAG", "address: "+address );
            Log.e("TAG", "address: "+city );
            Log.e("TAG", "address: "+state );
            Log.e("TAG", "address: "+postalCode );

            tvAddress.setText(" "+address);

            return  address;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void MarkAttendance(View view)
    {



        if(address(currentLocation.getLatitude(),currentLocation.getLongitude())!=null) {
            address = address(currentLocation.getLatitude(),currentLocation.getLongitude());
            // Toast.makeText(this, "Address : "+address+"\nTime : "+time+"\nDate : "+date, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Dashboard.class);
            intent.putExtra("address", address);
            intent.putExtra("date", date);
            intent.putExtra("time", time);
            intent.putExtra("lat", currentLocation.getLatitude());
            intent.putExtra("lang", currentLocation.getLongitude());

            app.preference().putAddress(address);
            app.preference().putDate(date);
            app.preference().putTime(time);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    app.db().logDao().insert(new Logs(
                            app.preference().getId(),
                            date, time,
                            currentLocation.getLatitude(),
                            currentLocation.getLongitude(),
                            address,
                            app.preference().getAPIUsername(),
                            app.preference().getAPIPassword(),
                            "not synced"));

                }
            }).start();


            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
            finish();

            Toast.makeText(this, "Please check internet connctivity", Toast.LENGTH_SHORT).show();
        }
    }



}