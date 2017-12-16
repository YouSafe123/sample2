package com.sample.com.sample2;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.*;
import java.util.jar.Manifest;
import java.util.logging.Handler;

public class UserActivity extends FragmentActivity implements OnMapReadyCallback{
TextView msg,par_name,locn,ttime;

GoogleMap mMap;
  //  String str;
    Geocoder geocoder;
    List<Address> addresses;
    Double latitude;
    Double longitude;
    private static final int MY_PERMISSION_REQUEST_LOCATION=1;
    String fulln,p_fulln,pid,gen,fullAddr,stat;
    final DatabaseReference mdatabase= FirebaseDatabase.getInstance().getReference();
    HashMap<String, String> dataMap=new HashMap<String, String>();
    @Override
    protected void onStart() {
        pid=getIntent().getStringExtra("USERID");
        Log.d("Getting pid start",pid);

        fulln=getIntent().getStringExtra("FULLNAME");
        p_fulln=getIntent().getStringExtra("PARENTNAME");
        dataMap.put("Latitude",latitude+"");
        dataMap.put("Longitude",longitude+"");
        gen=getIntent().getStringExtra("GENDER");
        geocoder=new Geocoder(this, Locale.getDefault());

        //--
        if(ContextCompat.checkSelfPermission(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION))
            {
                ActivityCompat.requestPermissions(UserActivity.this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
            else
            {
                ActivityCompat.requestPermissions(UserActivity.this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
        }
        else
        {
            LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try
            {
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                addresses=geocoder.getFromLocation(latitude,longitude,1);
                String address=addresses.get(0).getAddressLine(0);
                //String adminArea=addresses.get(0).getAdminArea();
                //String subAdminArea=addresses.get(0).getSubAdminArea();
                String subarea=addresses.get(0).getSubLocality();
                String area=addresses.get(0).getLocality();
                String city=addresses.get(0).getAdminArea();
                String country=addresses.get(0).getCountryName();
                String postalcode=addresses.get(0).getPostalCode();
                fullAddr=address+", "+subarea+", "+area+", "+city+", "+country+", "+postalcode;
                dataMap.put("Location",fullAddr);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(UserActivity.this,"Not Found!",Toast.LENGTH_LONG).show();

            }
        }
        //---

        dataMap.put("Full Name",fulln);
        dataMap.put("Parent Name",p_fulln);
        dataMap.put("Gender",gen);
        dataMap.put("Latitude",latitude+"");
        dataMap.put("Longitude",longitude+"");
        stat="Online";
        dataMap.put("Status",stat);
        long date=System.currentTimeMillis();
        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy\nhh:mm:ss a");
        String dateString=sdf.format(date);
        dataMap.put("Date-Time Stamp",dateString);
        mdatabase.child("States").child(pid).setValue(dataMap);
        Toast.makeText(UserActivity.this,"Started!",Toast.LENGTH_LONG).show();
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode)
        {
            case MY_PERMISSION_REQUEST_LOCATION:{
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                    {
                        LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
                        Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        try
                        {
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            addresses=geocoder.getFromLocation(latitude,longitude,1);
                            String address=addresses.get(0).getAddressLine(0);
                     //       String adminArea=addresses.get(0).getAdminArea();
                       //     String subAdminArea=addresses.get(0).getSubAdminArea();
                            String area=addresses.get(0).getLocality();
                            String subarea=addresses.get(0).getSubLocality();
                            String city=addresses.get(0).getAdminArea();
                            String country=addresses.get(0).getCountryName();
                            String postalcode=addresses.get(0).getPostalCode();
                            fullAddr=address+", "+subarea+", "+area+", "+city+", "+country+", "+postalcode;
                            dataMap.put("Location",fullAddr);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(UserActivity.this,"Not Found!",Toast.LENGTH_LONG).show();

                        }
                    }
                }
                else
                {
                    Toast.makeText(UserActivity.this,"No Permission Granted!",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        //status="Offline";
        pid=getIntent().getStringExtra("USERID");
        fulln=getIntent().getStringExtra("FULLNAME");
        p_fulln=getIntent().getStringExtra("PARENTNAME");
        dataMap.put("Latitude",latitude+"");
        dataMap.put("Longitude",longitude+"");
        gen=getIntent().getStringExtra("GENDER");
        geocoder=new Geocoder(this, Locale.getDefault());
        //--
        if(ContextCompat.checkSelfPermission(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION))
            {
                ActivityCompat.requestPermissions(UserActivity.this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
            else
            {
                ActivityCompat.requestPermissions(UserActivity.this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
        }
        else
        {
            LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try
            {
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                addresses=geocoder.getFromLocation(latitude,longitude,1);
                String address=addresses.get(0).getAddressLine(0);
               // String adminArea=addresses.get(0).getAdminArea();
                //String subAdminArea=addresses.get(0).getSubAdminArea();
                String area=addresses.get(0).getLocality();
                String subarea=addresses.get(0).getSubLocality();
                String city=addresses.get(0).getAdminArea();
                String country=addresses.get(0).getCountryName();
                String postalcode=addresses.get(0).getPostalCode();
                fullAddr=address+", "+subarea+", "+area+", "+city+", "+country+", "+postalcode;
                dataMap.put("Location",fullAddr);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(UserActivity.this,"Not Found!",Toast.LENGTH_LONG).show();

            }
        }
        //---
        dataMap.put("Full Name",fulln);
        dataMap.put("Parent Name",p_fulln);
        dataMap.put("Latitude",latitude+"");
        dataMap.put("Longitude",longitude+"");
        dataMap.put("Gender",gen);
        stat="Offline";
        dataMap.put("Status",stat);
        long date=System.currentTimeMillis();
        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy\nhh:mm:ss a");
        String dateString=sdf.format(date);
        dataMap.put("Date-Time Stamp",dateString);
        // dataMap.put("Location","Lucknow");
        mdatabase.child("States").child(pid).setValue(dataMap);
        Toast.makeText(UserActivity.this,"From Stop Ended!",Toast.LENGTH_LONG).show();
        super.onStop();
    }

    @Override
    protected void onPause() {
        //status="Offline";
        pid=getIntent().getStringExtra("USERID");
        fulln=getIntent().getStringExtra("FULLNAME");
        p_fulln=getIntent().getStringExtra("PARENTNAME");
        dataMap.put("Latitude",latitude+"");
        dataMap.put("Longitude",longitude+"");
        gen=getIntent().getStringExtra("GENDER");
        geocoder=new Geocoder(this, Locale.getDefault());
        //--
        if(ContextCompat.checkSelfPermission(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION))
            {
                ActivityCompat.requestPermissions(UserActivity.this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
            else
            {
                ActivityCompat.requestPermissions(UserActivity.this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
        }
        else
        {
            LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try
            {
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                addresses=geocoder.getFromLocation(latitude,longitude,1);
                String address=addresses.get(0).getAddressLine(0);
               // String adminArea=addresses.get(0).getAdminArea();
                //String subAdminArea=addresses.get(0).getSubAdminArea();
                String area=addresses.get(0).getLocality();
                String subarea=addresses.get(0).getSubLocality();
                String city=addresses.get(0).getAdminArea();
                String country=addresses.get(0).getCountryName();
                String postalcode=addresses.get(0).getPostalCode();
                fullAddr=address+", "+subarea+", "+area+", "+city+", "+country+", "+postalcode;
                dataMap.put("Location",fullAddr);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(UserActivity.this,"Not Found!",Toast.LENGTH_LONG).show();

            }
        }
        //---
        dataMap.put("Full Name",fulln);
        dataMap.put("Parent Name",p_fulln);
        dataMap.put("Gender",gen);
        dataMap.put("Latitude",latitude+"");
        dataMap.put("Longitude",longitude+"");
        stat="Offline";
        dataMap.put("Status",stat);
        long date=System.currentTimeMillis();
        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy\nhh:mm:ss a");
        String dateString=sdf.format(date);
        dataMap.put("Date-Time Stamp",dateString);
        //dataMap.put("Location","Lucknow");
        mdatabase.child("States").child(pid).setValue(dataMap);
        Toast.makeText(UserActivity.this,"From Pause Ended!",Toast.LENGTH_LONG).show();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        //status="Online";
        pid=getIntent().getStringExtra("USERID");
        fulln=getIntent().getStringExtra("FULLNAME");
        p_fulln=getIntent().getStringExtra("PARENTNAME");
        gen=getIntent().getStringExtra("GENDER");
        geocoder=new Geocoder(this, Locale.getDefault());
        //--
        if(ContextCompat.checkSelfPermission(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION))
            {
                ActivityCompat.requestPermissions(UserActivity.this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
            else
            {
                ActivityCompat.requestPermissions(UserActivity.this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
        }
        else
        {
            LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try
            {
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                addresses=geocoder.getFromLocation(latitude,longitude,1);
                String address=addresses.get(0).getAddressLine(0);
             //   String adminArea=addresses.get(0).getAdminArea();
               // String subAdminArea=addresses.get(0).getSubAdminArea();
                String area=addresses.get(0).getLocality();
                String subarea=addresses.get(0).getSubLocality();
                String city=addresses.get(0).getAdminArea();
                String country=addresses.get(0).getCountryName();
                String postalcode=addresses.get(0).getPostalCode();
                fullAddr=address+", "+subarea+", "+area+", "+city+", "+country+", "+postalcode;
                dataMap.put("Location",fullAddr);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(UserActivity.this,"Not Found!",Toast.LENGTH_LONG).show();

            }
        }
        //---
        dataMap.put("Full Name",fulln);
        dataMap.put("Parent Name",p_fulln);
        dataMap.put("Gender",gen);
        dataMap.put("Latitude",latitude+"");
        dataMap.put("Longitude",longitude+"");
        stat="Online";
        dataMap.put("Status",stat);
        long date=System.currentTimeMillis();
        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy\nhh:mm:ss a");
        String dateString=sdf.format(date);
        dataMap.put("Date-Time Stamp",dateString);
        //dataMap.put("Location","Lucknow");
        mdatabase.child("States").child(pid).setValue(dataMap);
        Toast.makeText(UserActivity.this,"From Restart Started!",Toast.LENGTH_LONG).show();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        //status="Online";
        pid=getIntent().getStringExtra("USERID");
        fulln=getIntent().getStringExtra("FULLNAME");
        p_fulln=getIntent().getStringExtra("PARENTNAME");
        gen=getIntent().getStringExtra("GENDER");
        geocoder=new Geocoder(this, Locale.getDefault());
        //--
        if(ContextCompat.checkSelfPermission(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION))
            {
                ActivityCompat.requestPermissions(UserActivity.this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
            else
            {
                ActivityCompat.requestPermissions(UserActivity.this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
        }
        else
        {
            LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try
            {
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                addresses=geocoder.getFromLocation(latitude,longitude,1);
                String address=addresses.get(0).getAddressLine(0);
              //  String adminArea=addresses.get(0).getAdminArea();
              //  String subAdminArea=addresses.get(0).getSubAdminArea();
                String area=addresses.get(0).getLocality();
                String subarea=addresses.get(0).getSubLocality();
                String city=addresses.get(0).getAdminArea();
                String country=addresses.get(0).getCountryName();
                String postalcode=addresses.get(0).getPostalCode();
                fullAddr=address+", "+subarea+", "+area+", "+city+", "+country+", "+postalcode;
                dataMap.put("Location",fullAddr);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(UserActivity.this,"Not Found!",Toast.LENGTH_LONG).show();

            }
        }
        //---
        dataMap.put("Full Name",fulln);
        dataMap.put("Parent Name",p_fulln);
        dataMap.put("Latitude",latitude+"");
        dataMap.put("Longitude",longitude+"");
        dataMap.put("Gender",gen);
        stat="Online";
        dataMap.put("Status",stat);
        long date=System.currentTimeMillis();
        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy\nhh:mm:ss a");
        String dateString=sdf.format(date);
        dataMap.put("Date-Time Stamp",dateString);
        //dataMap.put("Location","Lucknow");
        mdatabase.child("States").child(pid).setValue(dataMap);
        Toast.makeText(UserActivity.this,"From Resume Started!",Toast.LENGTH_LONG).show();

        super.onResume();
    }

    @Override
    protected void onDestroy() {
//        status="Offline";
        pid=getIntent().getStringExtra("USERID");
        fulln=getIntent().getStringExtra("FULLNAME");
        p_fulln=getIntent().getStringExtra("PARENTNAME");
        gen=getIntent().getStringExtra("GENDER");
        geocoder=new Geocoder(this, Locale.getDefault());
        //--
        if(ContextCompat.checkSelfPermission(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION))
            {
                ActivityCompat.requestPermissions(UserActivity.this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
            else
            {
                ActivityCompat.requestPermissions(UserActivity.this,new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
            }
        }
        else
        {
            LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try
            {
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                addresses=geocoder.getFromLocation(latitude,longitude,1);
                String address=addresses.get(0).getAddressLine(0);
               // String adminArea=addresses.get(0).getAdminArea();
               // String subAdminArea=addresses.get(0).getSubAdminArea();
                String area=addresses.get(0).getLocality();
                String subarea=addresses.get(0).getSubLocality();
                String city=addresses.get(0).getAdminArea();
                String country=addresses.get(0).getCountryName();
                String postalcode=addresses.get(0).getPostalCode();
                fullAddr=address+", "+subarea+", "+area+", "+city+", "+country+", "+postalcode;
                dataMap.put("Location",fullAddr);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(UserActivity.this,"Not Found!",Toast.LENGTH_LONG).show();

            }
        }
        //---
        dataMap.put("Full Name",fulln);
        dataMap.put("Parent Name",p_fulln);
        dataMap.put("Gender",gen);
        dataMap.put("Latitude",latitude+"");
        dataMap.put("Longitude",longitude+"");
        stat="Offline";
        dataMap.put("Status",stat);
        long date=System.currentTimeMillis();
        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy\nhh:mm:ss a");
        String dateString=sdf.format(date);
        dataMap.put("Date-Time Stamp",dateString);
        //dataMap.put("Location","Lucknow");
        mdatabase.child("States").child(pid).setValue(dataMap);
        Toast.makeText(UserActivity.this,"From destroy Ended!",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        msg=(TextView)findViewById(R.id.message);
        par_name=(TextView)findViewById(R.id.parent_name);
        ttime=(TextView)findViewById(R.id.timers);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fulln=getIntent().getStringExtra("FULLNAME");
        p_fulln=getIntent().getStringExtra("PARENTNAME");
        locn=(TextView)findViewById(R.id.locn);
        //--
        try {
            DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference("States");
            mdatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String s1 = ds.child("Full Name").getValue(String.class);
                        String s2 = ds.child("Parent Name").getValue(String.class);
                        if (fulln.equals(s1) && p_fulln.equals(s2)) {
                            String loc = ds.child("Location").getValue(String.class);
                            locn.setText(loc);
                        }
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //--

            msg.setText("Hi, " + fulln + "!");
            par_name.setText("Parent Name : " + p_fulln);


            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
//---
                    geocoder = new Geocoder(UserActivity.this, Locale.getDefault());
                    //--
                    if (ContextCompat.checkSelfPermission(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(UserActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                            ActivityCompat.requestPermissions(UserActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
                        } else {
                            ActivityCompat.requestPermissions(UserActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
                        }
                    } else {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        try {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            String address = addresses.get(0).getAddressLine(0);
                            // String adminArea=addresses.get(0).getAdminArea();
                            // String subAdminArea=addresses.get(0).getSubAdminArea();
                            String area = addresses.get(0).getLocality();
                            String subarea = addresses.get(0).getSubLocality();
                            String city = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();
                            String postalcode = addresses.get(0).getPostalCode();
                            fullAddr = address + ", " + subarea + ", " + area + ", " + city + ", " + country + ", " + postalcode;
//                    dataMap.put("Location",fullAddr);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(UserActivity.this, "Not Found!", Toast.LENGTH_LONG).show();

                        }
                    }
                    final DatabaseReference mdatabase_iterative = FirebaseDatabase.getInstance().getReference();
                    HashMap<String, String> dataMap_iterative = new HashMap<String, String>();
                    dataMap_iterative.put("Full Name", fulln);
                    dataMap_iterative.put("Parent Name", p_fulln);
                    dataMap_iterative.put("Location", fullAddr);
                    dataMap_iterative.put("Latitude", latitude + "");
                    dataMap_iterative.put("Longitude", longitude + "");
                    dataMap_iterative.put("Status", stat);
                    dataMap_iterative.put("Gender", gen);
                    dataMap_iterative.put("USERID", pid);
                    long date = System.currentTimeMillis();
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                    java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("dd-MM-yyyy\nhh:mm:ss a");
                    String dateString = sdf.format(date);
                    String dateString1 = sdf1.format(date);
                    dataMap_iterative.put("Date-Time Stamp", dateString1);
                    mdatabase_iterative.child("Logs").child(pid + " " + dateString + "").setValue(dataMap_iterative);


//---
                    // Log.d("PassingTime1",dateString);
                }
            }, 0, 60000);//put here time 1000 milliseconds=1 seco
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        while (!isInterrupted()) {
                            Thread.sleep(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    long date = System.currentTimeMillis();
                                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy\nhh:mm:ss a");
                                    String dateString = sdf.format(date);
                                    ttime.setText(dateString);

                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            };
            thread.start();
//
//        android.os.Handler handler=new android.os.Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//           // Log.d("PassingTime","Time");
//            }
//        },2000);
        }
        catch (Exception e)
        {
            Toast.makeText(UserActivity.this,"No Internet!",Toast.LENGTH_LONG).show();
        }

      //  onCreate(savedInstanceState);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("Checking","Main Entry");
      //  final String spin=getIntent().getStringExtra("CHILDNAME");
    //    final String fulln=getIntent().getStringExtra("PARENTNAME");
        //str=spin+"'s Current Location";
        DatabaseReference mdatabase= FirebaseDatabase.getInstance().getReference("States");
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String s1=ds.child("Full Name").getValue(String.class);
                    String s2=ds.child("Parent Name").getValue(String.class);
                    if(s1.equals(fulln)&&s2.equals(p_fulln))
                    {

                        double latitude=Double.parseDouble(ds.child("Latitude").getValue(String.class));
                        double longitude=Double.parseDouble(ds.child("Longitude").getValue(String.class));
                        // Add a marker in Sydney and move the camera
                        LatLng locate = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(locate).title("Your Current Location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(locate));
                        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
                        Log.d("Checking","pass");
                        //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locate),14);
                        //   mMap.animateCamera(CameraUpdateFactory.zoomIn());
                    }}}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
