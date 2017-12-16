package com.sample.com.sample2;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView lv;
    TextView n,lat,longi,locn,pn,stat,ts,txt_n,
            txt_rbtn,txt_lat,txt_longi,textView8,
            txt_loc,txt_pn,txt_stat,txt_ts,txt_gen;
    RadioButton gen_rbtn;
    Spinner user_spin,dt_spin;
    ArrayList<String> mUsernames=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//keyval=(TextView)findViewById(R.id.keyval);
        user_spin=(Spinner)findViewById(R.id.user_spin);
        dt_spin=(Spinner)findViewById(R.id.dt_spin);
        txt_n=(TextView)findViewById(R.id.textView11);
        textView8=(TextView)findViewById(R.id.textView8);
        txt_rbtn=(TextView)findViewById(R.id.teiew8);
        txt_lat=(TextView)findViewById(R.id.textView15);
        txt_longi=(TextView)findViewById(R.id.textView17);
        txt_loc=(TextView)findViewById(R.id.textView19);
        txt_pn=(TextView)findViewById(R.id.textVi19);
        txt_stat=(TextView)findViewById(R.id.txt_stat);
        txt_ts=(TextView)findViewById(R.id.textView25);
        txt_gen=(TextView)findViewById(R.id.textView13);

        txt_n.setVisibility(View.GONE);
        txt_rbtn.setVisibility(View.GONE);
        txt_lat.setVisibility(View.GONE);
        txt_longi.setVisibility(View.GONE);
        txt_loc.setVisibility(View.GONE);
        textView8.setVisibility(View.GONE);
        txt_pn.setVisibility(View.GONE);
        txt_stat.setVisibility(View.GONE);
        txt_ts.setVisibility(View.GONE);
        txt_gen.setVisibility(View.GONE);


        n=(TextView)findViewById(R.id.n);
        lat=(TextView)findViewById(R.id.lat);
        longi=(TextView)findViewById(R.id.longi);
        locn=(TextView)findViewById(R.id.locn);
        pn=(TextView)findViewById(R.id.pn);
        stat=(TextView)findViewById(R.id.stat);
        ts=(TextView)findViewById(R.id.ts);
        gen_rbtn=(RadioButton)findViewById(R.id.gen_rbtn);

        n.setVisibility(View.GONE);
        lat.setVisibility(View.GONE);
        longi.setVisibility(View.GONE);
        locn.setVisibility(View.GONE);
        pn.setVisibility(View.GONE);
        stat.setVisibility(View.GONE);
        ts.setVisibility(View.GONE);
        gen_rbtn.setVisibility(View.GONE);
        dt_spin.setVisibility(View.GONE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//.setTypeface(null, Typeface.BOLD)
























        lv=(ListView) findViewById(R.id.user_list);
        lv.setVisibility(View.VISIBLE);
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mUsernames);
         lv.setAdapter(arrayAdapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            lv.setVisibility(View.VISIBLE);
            textView8.setVisibility(View.GONE);
            user_spin.setVisibility(View.GONE);

            final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mUsernames);
             lv.setAdapter(arrayAdapter);
            final DatabaseReference mdatabase= FirebaseDatabase.getInstance().getReference("Users");
          //  Toast.makeText(AdminActivity.this,"Fetching Parent data... Please Wait!",Toast.LENGTH_LONG).show();
            ValueEventListener valueEventListener = mdatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String user=ds.getKey();
                        String fn = ds.child("Full Name").getValue(String.class);
                        String gen = ds.child("Gender").getValue(String.class);
                        String addr = ds.child("Address").getValue(String.class);
                        String pass = ds.child("Password").getValue(String.class);
                        String mob = ds.child("Mobile No").getValue(String.class);

                        Log.d("Passing : ","Going!!!");
                        mUsernames.add(Html.fromHtml("<b>UserID : </b>") + user + "\nName : " + fn + " \nGender : " + gen + "\nAddress : " + addr + "\nPassword : " + pass + "\nMobile No. : " + mob);

                        lv.setAdapter(arrayAdapter);
                        Log.d("Passing : ","done!!!");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mdatabase.goOffline();
            // Handle the camera action
        }
        else {
            if (id == R.id.nav_gallery) {
//    Log.d("Passing : ","Sector Going!!!");
                final DatabaseReference mdatabase1 = FirebaseDatabase.getInstance().getReference("Childs");
                final ArrayList<String> mUsernames1 = new ArrayList<>();
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsernames1);
                lv.setAdapter(arrayAdapter);
           //     Toast.makeText(AdminActivity.this, "Loading Child data... Please Wait!", Toast.LENGTH_LONG).show();

                mdatabase1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String user = ds.getKey();
                            String fn = ds.child("Full Name").getValue(String.class);
                            String gen = ds.child("Gender").getValue(String.class);
                            String addr = ds.child("Address").getValue(String.class);
                            String pass = ds.child("Password").getValue(String.class);
                            String mob = ds.child("Mobile No").getValue(String.class);
                            String pn = ds.child("Parent").getValue(String.class);
                            String pnid = ds.child("ParentID").getValue(String.class);
                            String email = ds.child("EmailID").getValue(String.class);
                            Log.d("Passing : ", "Child Going!!!");
                            mUsernames1.add(Html.fromHtml("<b>UserID : </b>") + user + "\nName : " + fn + " \nGender : " + gen + "\nAddress : " + addr + "\nPassword : " + pass + "\nMobile No. : " + mob + "\nParent Name : " + pn + "\nParent ID : " + pnid + "\nEmail ID : " + email);
                            lv.setAdapter(arrayAdapter);
                            Log.d("Passing : ", "Child done!!!");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                if (id == R.id.nav_slideshow) {

                } else {
                    if (id == R.id.nav_manage) {
                        //--
                        lv.setVisibility(View.GONE);
                        textView8.setVisibility(View.VISIBLE);
                        user_spin.setVisibility(View.VISIBLE);
                        final ArrayList<String> mUsernames = new ArrayList<>();
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsernames);
                        mUsernames.add("- - - select - - -");
                        final DatabaseReference mdatabase_spin1 = FirebaseDatabase.getInstance().getReference("Childs");
                        mdatabase_spin1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String CID = ds.getKey();
                                    mUsernames.add(CID);
                                }
                                user_spin.setAdapter(arrayAdapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
user_spin.setOnItemSelectedListener(new OnItemSelectedListener() {
 @Override
 public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
  //   String workRequestType = adapterView.getItemAtPosition(pos).toString();


     int position_of_user_spin=user_spin.getSelectedItemPosition();
     if(position_of_user_spin!=0)
     {
         txt_rbtn.setVisibility(View.VISIBLE);
         dt_spin.setVisibility(View.VISIBLE);
     }
     else
     {
         txt_rbtn.setVisibility(View.GONE);
         dt_spin.setVisibility(View.GONE);
         txt_n.setVisibility(View.GONE);
       //  txt_rbtn.setVisibility(View.GONE);
         txt_lat.setVisibility(View.GONE);
         txt_longi.setVisibility(View.GONE);
         txt_loc.setVisibility(View.GONE);
         txt_pn.setVisibility(View.GONE);
         txt_stat.setVisibility(View.GONE);
         txt_ts.setVisibility(View.GONE);
         txt_gen.setVisibility(View.GONE);

         n.setVisibility(View.GONE);
         lat.setVisibility(View.GONE);
         longi.setVisibility(View.GONE);
         locn.setVisibility(View.GONE);
         pn.setVisibility(View.GONE);
         stat.setVisibility(View.GONE);
         ts.setVisibility(View.GONE);
         gen_rbtn.setVisibility(View.GONE);
       //  dt_spin.setVisibility(View.GONE);
         Toast.makeText(AdminActivity.this,"Child Not Selected!",Toast.LENGTH_LONG).show();
     }
 final ArrayList<String> mUsernames_inner = new ArrayList<>();
 final ArrayAdapter<String> arrayAdapter_inner = new ArrayAdapter<String>(AdminActivity.this, android.R.layout.simple_list_item_1, mUsernames_inner);
 mUsernames_inner.add("- - - select - - -");
     final String matcher=user_spin.getSelectedItem().toString();
final DatabaseReference mdatabase_spin2 = FirebaseDatabase.getInstance().getReference("Logs");
mdatabase_spin2.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
        String user = ds.child("USERID").getValue(String.class);
        if(matcher.equals(user))
        {
            String dt = ds.child("Date-Time Stamp").getValue(String.class);
            mUsernames_inner.add(dt);
        }
dt_spin.setAdapter(arrayAdapter_inner);

        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});

 }

 @Override
 public void onNothingSelected(AdapterView<?> adapterView) {

 }

});


dt_spin.setOnItemSelectedListener(new OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        final ArrayList<String> mUsernames_inner = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter_inner = new ArrayAdapter<String>(AdminActivity.this, android.R.layout.simple_list_item_1, mUsernames_inner);
        mUsernames_inner.add("- - - select - - -");
        int position_of_dt_spin=dt_spin.getSelectedItemPosition();
        if(position_of_dt_spin!=0)
        {
            txt_n.setVisibility(View.VISIBLE);
            //  txt_rbtn.setVisibility(View.GONE);
            txt_lat.setVisibility(View.VISIBLE);
            txt_longi.setVisibility(View.VISIBLE);
            txt_loc.setVisibility(View.VISIBLE);
            txt_pn.setVisibility(View.VISIBLE);
            txt_stat.setVisibility(View.VISIBLE);
            txt_ts.setVisibility(View.VISIBLE);
            txt_gen.setVisibility(View.VISIBLE);
            n.setVisibility(View.VISIBLE);
            lat.setVisibility(View.VISIBLE);
            longi.setVisibility(View.VISIBLE);
            locn.setVisibility(View.VISIBLE);
            pn.setVisibility(View.VISIBLE);
            stat.setVisibility(View.VISIBLE);
            ts.setVisibility(View.VISIBLE);
            gen_rbtn.setVisibility(View.VISIBLE);
        }
        else
        {
            txt_n.setVisibility(View.GONE);
            //  txt_rbtn.setVisibility(View.GONE);
            txt_lat.setVisibility(View.GONE);
            txt_longi.setVisibility(View.GONE);
            txt_loc.setVisibility(View.GONE);
            txt_pn.setVisibility(View.GONE);
            txt_stat.setVisibility(View.GONE);
            txt_ts.setVisibility(View.GONE);
            txt_gen.setVisibility(View.GONE);
            n.setVisibility(View.GONE);
            lat.setVisibility(View.GONE);
            longi.setVisibility(View.GONE);
            locn.setVisibility(View.GONE);
            pn.setVisibility(View.GONE);
            stat.setVisibility(View.GONE);
            ts.setVisibility(View.GONE);
            gen_rbtn.setVisibility(View.GONE);
            Toast.makeText(AdminActivity.this,"Date-Time Not Selected!",Toast.LENGTH_LONG).show();
        }
        final String matcher=dt_spin.getSelectedItem().toString();
        final DatabaseReference mdatabase_spin2 = FirebaseDatabase.getInstance().getReference("Logs");
        mdatabase_spin2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    String user = ds.child("USERID").getValue(String.class);
                    String fn = ds.child("Full Name").getValue(String.class);
                    String gen = ds.child("Gender").getValue(String.class);
                    String latitude = ds.child("Latitude").getValue(String.class);
                    String longitude = ds.child("Longitude").getValue(String.class);
                    String location = ds.child("Location").getValue(String.class);
                    String ptn = ds.child("Parent Name").getValue(String.class);
                    String status = ds.child("Status").getValue(String.class);
                    String datetime = ds.child("Date-Time Stamp").getValue(String.class);
                    if(datetime.equals(matcher))
                    {

//                        keyval.setText(key);
                        n.setText(fn);
                        gen_rbtn.setText(gen);
                        lat.setText(latitude);
                        longi.setText(longitude);
                        locn.setText(location);
                        pn.setText(ptn);
                        stat.setText(status);
                        ts.setText(datetime);
                    }
                }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});
/*
                        turned off
                        final DatabaseReference mdatabase1 = FirebaseDatabase.getInstance().getReference("Logs");

                        final ArrayList<String> mUsernames1 = new ArrayList<>();
                        final ArrayAdapter<String> arrayAdapter12 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsernames1);
                        lv.setAdapter(arrayAdapter);
                        lv.setVisibility(View.GONE);

                        Toast.makeText(AdminActivity.this, "Loading Logs...", Toast.LENGTH_LONG).show();

                        mdatabase1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String log = ds.getKey();
                                    String fn = ds.child("Full Name").getValue(String.class);


                                    String dt_tim_stmp = ds.child("Date-Time Stamp").getValue(String.class);

                                    String gen = ds.child("Gender").getValue(String.class);
                                    String latitude = ds.child("Latitude").getValue(String.class);
                                    String longitude = ds.child("Longitude").getValue(String.class);
                                    String location = ds.child("Location").getValue(String.class);
                                    String pn = ds.child("Parent Name").getValue(String.class);
                                    String status = ds.child("Status").getValue(String.class);
                                    String userid = ds.child("USERID").getValue(String.class);
                                    // String email=ds.child("EmailID").getValue(String.class);
                                    //Log.d("Passing : ","Child Going!!!");
                                    mUsernames1.add(Html.fromHtml("\bKey : " + log + "\b\n\n<b>UserID : </b>") + userid + "\nName : " + fn
                                            + " \nGender : " + gen + "\nLatitude : " + latitude +
                                            "\nLongitude : " + longitude
                                            + "\nLocation : " + location + "\nParent Name : " + pn + "\nStatus : " + status +
                                            "\nDate-Time Stamp : " + dt_tim_stmp);
                                    lv.setAdapter(arrayAdapter12);
                                    //Log.d("Passing : ","Child done!!!");

                    }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
*/

                        //lv.setVisibility(View.GONE);

                        //--
                    } else if (id == R.id.nav_share) {

                    } else if (id == R.id.nav_send) {

                    }
                }
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
