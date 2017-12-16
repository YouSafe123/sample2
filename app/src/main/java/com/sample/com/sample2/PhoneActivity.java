package com.sample.com.sample2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PhoneActivity extends AppCompatActivity {
    private static final String TAG = "PhoneLogin";
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    TextView t1,t2;
    ImageView i1;
    EditText e1,e2;
    long count=0;
    Button b1,b2;
String s1,s2,s3,s4,s5,s6,s7,s8;
    private String email, subject, message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        e1 = (EditText) findViewById(R.id.Phonenoedittext);
        b1 = (Button) findViewById(R.id.PhoneVerify);
        t1 = (TextView)findViewById(R.id.textView2Phone);
        i1 = (ImageView)findViewById(R.id.imageView2Phone);
        e2 = (EditText) findViewById(R.id.OTPeditText);
        b2 = (Button)findViewById(R.id.OTPVERIFY);
        t2 = (TextView)findViewById(R.id.textViewVerified);

        //-----
        s1=getIntent().getStringExtra("FULLNAME");
        s2=getIntent().getStringExtra("GENDER");
        s3=getIntent().getStringExtra("DATEOFBIRTH");
        s4=getIntent().getStringExtra("PASSWORD");
        s5=getIntent().getStringExtra("ADDRESS");
        s6=getIntent().getStringExtra("MOBILENO");
        s7=getIntent().getStringExtra("COUNTER");
        s8=getIntent().getStringExtra("EMAILID");
//        Log.d("S1 : ",""+s1+"\n");
//        Log.d("S2 : ",""+s2+"\n");
//        Log.d("S3 : ",""+s3+"\n");
//        Log.d("S4 : ",""+s4+"\n");
//        Log.d("S5 : ",""+s5+"\n");
//        Log.d("S6 : ",""+s6+"\n");
//        Log.d("S7 : ",""+s7+"\n");

        e1.setText("+91"+s6);



        //-----

        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;
                Toast.makeText(PhoneActivity.this,"Verification Complete",Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // Log.w(TAG, "onVerificationFailed", e);
                Toast.makeText(PhoneActivity.this,"Verification Failed",Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(PhoneActivity.this,"InValid Phone Number",Toast.LENGTH_SHORT).show();
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // Log.d(TAG, "onCodeSent:" + verificationId);
                Toast.makeText(PhoneActivity.this,"Verification code has been send on your number",Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                e1.setVisibility(View.GONE);
                b1.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);
                i1.setVisibility(View.GONE);
                t2.setVisibility(View.VISIBLE);
                e2.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
                // ...
            }
        };

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        e1.getText().toString(),
                        60,
                        java.util.concurrent.TimeUnit.SECONDS,
                        PhoneActivity.this,
                        mCallbacks);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, e2.getText().toString());
                // [END verify_with_code]
                signInWithPhoneAuthCredential(credential);
            }
        });


    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //-----
                            final DatabaseReference mdatabase= FirebaseDatabase.getInstance().getReference();
                            HashMap<String, String>dataMap=new HashMap<String, String>();
                            //String key=mdatabase.child("Users").child("U1").push().getKey();
                            //dataMap.put("ID",key);
                            dataMap.put("Type","Parent");
                            dataMap.put("Full Name",s1);
                            dataMap.put("Gender",s2);
                            dataMap.put("Date of Birth",s3);
                            dataMap.put("Password",s4);
                            dataMap.put("Address",s5);
                            dataMap.put("Mobile No",s6);
                            dataMap.put("EmailID",s8);
count=Integer.parseInt(s7);

                            mdatabase.child("Users").child("U"+count).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                  String x="U"+count;
                                    if(task.isSuccessful())
                                    {

                                        Toast.makeText(PhoneActivity.this,"Verification Done",Toast.LENGTH_SHORT).show();
                                        mdatabase.child("counter").setValue(++count);
                                        Toast.makeText(PhoneActivity.this, "Registered Successfully!",Toast.LENGTH_LONG).show();
                                        //Creating SendMail object
                                        email=s8;
                                        subject="Registration Confirmation";
                                        message="\nDear "+s1+",\n\nCongratulations! You have been registered successfully on ICare!!\n\n"+"Your Credential Details:-\n\n- - - - - - - - - - \nUSERNAME : "+x+"\nPASSWORD : "+s4+"\n- - - - - - - - - - \n\n" +"Best regards,\n" +
                                            "ICare Team\n\n"+
                                                "Note: This is a system generated e-mail, " +
                                                "no need to reply.\n\n"+"*** This message is intended only for the person or entity to which it is addressed and may contain confidential and/or privileged information. If you have received this message in error," +
                                                " please notify the sender immediately " +
                                                "and delete this message from your system ***";
                                        SendMail sm = new SendMail(PhoneActivity.this, email, subject, message);

                                        //Executing sendmail to send email
                                        sm.execute();
                                        Intent i=new Intent(PhoneActivity.this,HomeActivity.class);
                                        i.putExtra("USERID",x);
                                        i.putExtra("FULLNAME",s1);
                                        startActivity(i);

                                    }
                                    else
                                        Toast.makeText(PhoneActivity.this, "Some error... Retry!",Toast.LENGTH_LONG).show();
                                }});

                            //-----
                            // Log.d(TAG, "signInWithCredential:success");
                            // ...
                        } else {
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(PhoneActivity.this,"Invalid Verification",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}
