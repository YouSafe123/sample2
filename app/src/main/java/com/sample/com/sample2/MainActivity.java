package com.sample.com.sample2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button signup,login;
    String username;
    String password;
    boolean auth=false;
    EditText user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        signup=(Button)findViewById(R.id.btn2);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        login=(Button)findViewById(R.id.btn1);

        login.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
         user = (EditText) findViewById(R.id.et1);
         pass = (EditText) findViewById(R.id.et2);
                 username = user.getText().toString();
                 password = pass.getText().toString();
                 if (username.equals("admin") && password.equals("admin")) {
                     auth=true;
                   //  Log.d("Passing : ","done!!!from Main");
Intent i = new Intent(MainActivity.this, AdminActivity.class);
startActivity(i);
                 } else {
     DatabaseReference mdatabase= FirebaseDatabase.getInstance().getReference("Users");
     mdatabase.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             for(DataSnapshot ds : dataSnapshot.getChildren()) {
                 String user = ds.getKey();
                 String nm=ds.child("Full Name").getValue(String.class);

                 String pass = ds.child("Password").getValue(String.class);
//       Log.i("UserName : ",""+user);
//                      System.out.println("Password : "+pass);

                 if (username.equalsIgnoreCase(user) && password.equalsIgnoreCase(pass)) {
                       Intent i = new Intent(MainActivity.this, HomeActivity.class);
                     i.putExtra("USERID",user);
                     i.putExtra("FULLNAME",nm);
                     Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                     auth = true;
                       startActivity(i);
                     break;
                 }

             }

         }


         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });

                     mdatabase.goOffline();
          DatabaseReference mdatabase1= FirebaseDatabase.getInstance().getReference("Childs");
     mdatabase1.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             for(DataSnapshot ds : dataSnapshot.getChildren()) {
                 String user = ds.getKey();
                 String nm=ds.child("Full Name").getValue(String.class);
                 String pass = ds.child("Password").getValue(String.class);
                 String p_nm=ds.child("Parent").getValue(String.class);
                 String gen=ds.child("Gender").getValue(String.class);
//       Log.i("UserName : ",""+user);
//                      System.out.println("Password : "+pass);

                 if (username.equalsIgnoreCase(user) && password.equalsIgnoreCase(pass)) {
                     Intent i = new Intent(MainActivity.this, UserActivity.class);
                     i.putExtra("USERID",user);
                     i.putExtra("FULLNAME",nm);
                     i.putExtra("PARENTNAME",p_nm);
                     i.putExtra("GENDER",gen);
                     Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                     auth = true;
                     startActivity(i);
                     break;
                 }


         }
             if(auth==false)
                 Toast.makeText(getApplicationContext(), "Invalid login!", Toast.LENGTH_SHORT).show();

         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });

 }


//                    if(auth==false)
//                        Toast.makeText(getApplicationContext(), "Invalid login!", Toast.LENGTH_SHORT).show();


}


}
);


}
}
