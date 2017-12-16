package com.sample.com.sample2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddChildActivity extends AppCompatActivity {
TextView fn,password,address,mobile,dt;
    EditText parentn,parentc,emailID;
    Calendar mCurrentDate;
    String pid;
    RadioGroup gender;
    long count=0;
    Button regbtn;
    int day, month,year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        pid= getIntent().getStringExtra("PID");
        emailID=(EditText)findViewById(R.id.email);
        final String p_user= getIntent().getStringExtra("PNAME");
//Log.d("getting : ",p_user);
        parentn=(EditText)findViewById(R.id.parent_name);
        parentn.setText(p_user);
        dt=(TextView) findViewById(R.id.datepickertxt);
        regbtn=(Button)findViewById(R.id.reg_btn);
        mCurrentDate= Calendar.getInstance();
        day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month=mCurrentDate.get(Calendar.MONTH);
        year=mCurrentDate.get(Calendar.YEAR);
        dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddChildActivity.this, new DatePickerDialog.OnDateSetListener()
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


        final DatabaseReference mdatabase_counter= FirebaseDatabase.getInstance().getReference();
        mdatabase_counter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = dataSnapshot.child("child_counter").getValue(Integer.class);
                Log.d("Add child Counter : ",""+count+"\n");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

regbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        fn=(TextView)findViewById(R.id.full_name);

        parentc=(EditText)findViewById(R.id.catg);

        gender=(RadioGroup)findViewById(R.id.gen);
        password=(TextView)findViewById(R.id.pass);
        address=(TextView)findViewById(R.id.add);
        mobile=(TextView)findViewById(R.id.mob_no);
        final String fulln=fn.getText().toString();
        int index=gender.getCheckedRadioButtonId();
        EditText category=(EditText)findViewById(R.id.catg);
        RadioButton radioButton = (RadioButton) findViewById(index);
        final String val=radioButton.getText().toString();
        final String pfn=parentn.getText().toString();
        final String pas=password.getText().toString();
        final String adr=address.getText().toString();
        final String mob=mobile.getText().toString();
        Intent i=new Intent(AddChildActivity.this, ChildPhoneActivity.class);
        i.putExtra("PARENTFULLNAME",pfn);
        i.putExtra("PARENTID",pid);
        i.putExtra("CATEGORY",category.getText().toString());
        i.putExtra("FULLNAME",fulln);
        i.putExtra("GENDER",val);
        i.putExtra("DATEOFBIRTH",dt.getText().toString());
        i.putExtra("PASSWORD",pas);
        i.putExtra("ADDRESS",adr);
        i.putExtra("MOBILENO",mob);
        i.putExtra("EMAILID",emailID.getText().toString());
        String conv=""+count;
        i.putExtra("COUNTER",conv);
        Log.d("Add child A() Counter: ",""+count+"\n");
        startActivity(i);

    }
});
    }
}
