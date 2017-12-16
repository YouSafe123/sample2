package com.sample.com.sample2;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
public class RegisterActivity extends AppCompatActivity {
    TextView fn,password,address,mobile,dt;
    RadioGroup gender;
    EditText emailID;
    Calendar mCurrentDate;
    int day, month,year;
    Button regbtn;
    long count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regbtn=(Button)findViewById(R.id.reg_btn);
        dt=(TextView)findViewById(R.id.datepickertxt);
        mCurrentDate=Calendar.getInstance();
        day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month=mCurrentDate.get(Calendar.MONTH);
        year=mCurrentDate.get(Calendar.YEAR);
        // month=month+1;
        //dt.setText(day+"/"+month+"/"+year);
        dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        monthOfYear=monthOfYear+1;
                        dt.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
                    }

                },year, month, day
                );
                datePickerDialog.show();

            }
        });

        final DatabaseReference mdatabase_counter=FirebaseDatabase.getInstance().getReference();
        mdatabase_counter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = dataSnapshot.child("counter").getValue(Integer.class);
Log.d("Before Counter : ",""+count+"\n");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fn=(TextView)findViewById(R.id.full_name);
                gender=(RadioGroup)findViewById(R.id.gen);
                password=(TextView)findViewById(R.id.pass);
                address=(TextView)findViewById(R.id.add);
                mobile=(TextView)findViewById(R.id.mob_no);
                final String fulln=fn.getText().toString();
                int index=gender.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(index);
                final String val=radioButton.getText().toString();
                emailID=(EditText)findViewById(R.id.email);
                final String pas=password.getText().toString();
                final String adr=address.getText().toString();
                final String mob=mobile.getText().toString();
                Intent i=new Intent(RegisterActivity.this, PhoneActivity.class);
                i.putExtra("FULLNAME",fulln);
                i.putExtra("GENDER",val);
                i.putExtra("DATEOFBIRTH",dt.getText().toString());
                i.putExtra("PASSWORD",pas);
                i.putExtra("ADDRESS",adr);
                i.putExtra("MOBILENO",mob);
                i.putExtra("EMAILID",emailID.getText().toString());
                String conv=""+count;
                i.putExtra("COUNTER",conv);
                Log.d("After Counter : ",""+count+"\n");
                startActivity(i);
                    }
                });







    }

}
