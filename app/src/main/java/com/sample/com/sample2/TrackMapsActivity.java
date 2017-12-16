package com.sample.com.sample2;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.ValueEventListener;

public class TrackMapsActivity extends FragmentActivity implements OnMapReadyCallback {
String str;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_track);
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
        Log.d("Checking","Main Entry");
        final String spin=getIntent().getStringExtra("CHILDNAME");
        final String fulln=getIntent().getStringExtra("PARENTNAME");
        //str=spin+"'s Current Location";
        DatabaseReference mdatabase= FirebaseDatabase.getInstance().getReference("States");
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String s1=ds.child("Full Name").getValue(String.class);
                    String s2=ds.child("Parent Name").getValue(String.class);
                    if(s1.equals(spin)&&s2.equals(fulln))
                    {
                        Log.d("Checking","Entry");
                        String gen=ds.child("Gender").getValue(String.class);
                        if(gen.equals("Female"))
                            str="Her";
                        else
                            str="His";
                        double latitude=Double.parseDouble(ds.child("Latitude").getValue(String.class));
                        double longitude=Double.parseDouble(ds.child("Longitude").getValue(String.class));
                        // Add a marker in Sydney and move the camera
                LatLng locate = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(locate).title(str+" Current Location"));
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
