package com.sample.com.sample2;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextMessage,text_status,text_locn,status,locn,timer,lbl_timer;
Button add,track;
    Spinner spin;
    String user,fulln;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        text_status=(TextView)findViewById(R.id.textView3);
        status=(TextView)findViewById(R.id.status);
        text_locn=(TextView)findViewById(R.id.textView5);
        locn=(TextView)findViewById(R.id.locn);
        status.setText("Retrieving Data...");
        locn.setText("Retrieving Data...");
        timer=(TextView)findViewById(R.id.ttime);
        user=getIntent().getStringExtra("USERID");
        fulln=getIntent().getStringExtra("FULLNAME");
        mTextMessage=(TextView)findViewById(R.id.message);
        mTextMessage.setText("Hi, "+fulln+"!");
        add=(Button)findViewById(R.id.add_btn);
        spin=(Spinner)findViewById(R.id.spinner);
        lbl_timer=(TextView)findViewById(R.id.textView6);
        track=(Button)findViewById(R.id.track_btn);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this,TrackMapsActivity.class);
                i.putExtra("PARENTNAME",fulln);
                i.putExtra("CHILDNAME",spin.getSelectedItem().toString());
                startActivity(i);
            }
        });
        //--testing
        final ArrayList<String> mUsernames=new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mUsernames);
        mUsernames.add("- - - select - - -");
        final DatabaseReference mdatabase= FirebaseDatabase.getInstance().getReference("Childs");
        Toast.makeText(HomeActivity.this,"Fetching data... Please Wait!",Toast.LENGTH_LONG).show();
        ValueEventListener valueEventListener = mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                   // String user=ds.getKey();
                    String pnid = ds.child("ParentID").getValue(String.class);
                    if(user.equals(pnid)) {
                        String fn = ds.child("Full Name").getValue(String.class);
                     //   Log.d("Passing : ", "Going!!!");
                        mUsernames.add(fn);
                    }
                    spin.setAdapter(arrayAdapter);
                    //Log.d("Passing : ","done!!!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        //String names[]={"- - select - -","DEF","GHI"};
        //ArrayAdapter <String> adapter;
        //adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);

        spin.setAdapter(arrayAdapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int switch_loop=0;
        switch(position)
{
    case 0:
        add.setVisibility(View.VISIBLE);
        track.setVisibility(View.GONE);
        text_status.setVisibility(View.GONE);
        text_locn.setVisibility(View.GONE);
        status.setVisibility(View.GONE);
        locn.setVisibility(View.GONE);
        timer.setVisibility(View.GONE);
        lbl_timer.setVisibility(View.GONE);
        switch_loop++;
        break;
}
if(switch_loop==0) {
    add.setVisibility(View.GONE);
    text_status.setVisibility(View.VISIBLE);
    text_locn.setVisibility(View.VISIBLE);
    status.setVisibility(View.VISIBLE);
    locn.setVisibility(View.VISIBLE);
    //timer.setVisibility(View.VISIBLE);
    //--
    DatabaseReference mdatabase= FirebaseDatabase.getInstance().getReference("States");
//  final ArrayList<String> mUsernames=new ArrayList<>();
    mdatabase.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
//            String user1 = ds.getKey();
    String s1=ds.child("Full Name").getValue(String.class);
    String s2=ds.child("Parent Name").getValue(String.class);
if(s1.equals(spin.getSelectedItem().toString())&&s2.equals(fulln))
{
    String loc=ds.child("Location").getValue(String.class);
    String gen=ds.child("Gender").getValue(String.class);
    String ttime=ds.child("Date-Time Stamp").getValue(String.class);
    String stat=ds.child("Status").getValue(String.class);
    locn.setText(loc);
    if(stat.equals("Online"))
    {
        if(gen.equals("Female"))
        {
            status.setText("She is Online!");
            status.setTextColor(Color.GREEN);
            track.setVisibility(View.VISIBLE);
            timer.setVisibility(View.GONE);
            lbl_timer.setVisibility(View.GONE);
            //status.setTextColor(Color.parseColor("#212121"));
        }
        else
        {
            status.setText("He is Online!");
            status.setTextColor(Color.GREEN);
            track.setVisibility(View.VISIBLE);
            timer.setVisibility(View.GONE);
            lbl_timer.setVisibility(View.GONE);
        }
    }
    else
    {
        if(gen.equals("Female"))
        {
            status.setText("She went Offline!");
            status.setTextColor(Color.RED);
            timer.setText(ttime);
            track.setVisibility(View.GONE);
            timer.setVisibility(View.VISIBLE);
            lbl_timer.setVisibility(View.VISIBLE);
        }
        else
        {
            status.setText("He went Offline!");
            status.setTextColor(Color.RED);
            timer.setText(ttime);
            timer.setVisibility(View.VISIBLE);
            track.setVisibility(View.GONE);
            lbl_timer.setVisibility(View.VISIBLE);
        }
    }
    //status.setText(stat);
}
        }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    //--

}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                add.setVisibility(View.VISIBLE);
                track.setVisibility(View.GONE);
                text_status.setVisibility(View.GONE);
                text_locn.setVisibility(View.GONE);
                status.setVisibility(View.GONE);
                locn.setVisibility(View.GONE);
               // timer.setVisibility(View.GONE);
            }


        });
        //--testing-done
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this, AddChildActivity.class);
                i.putExtra("PNAME",fulln);
               // Log.d("passing : ",fulln);
                i.putExtra("PID",user);
                startActivity(i);
            }
        });

        Thread thread=new Thread(){
        @Override
                public void run()
        {
            try{
                while(!isInterrupted())
                {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView ttime=(TextView)findViewById(R.id.timers);
                            long date=System.currentTimeMillis();
                            java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy\nhh:mm:ss a");
                            String dateString=sdf.format(date);
                            ttime.setText(dateString);
                        }
                    });
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    };
    thread.start();
//        TextView ttime=(TextView)findViewById(R.id.timers);
//ttime.setText((CharSequence) ServerValue.TIMESTAMP);

    }


}
