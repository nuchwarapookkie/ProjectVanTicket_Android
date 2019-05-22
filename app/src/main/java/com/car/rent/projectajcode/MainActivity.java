    package com.car.rent.projectajcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;


    public class MainActivity extends AppCompatActivity {
    EditText in_email;
    EditText in_pass;
    Button btn_lo;
    Button btn_regis;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    SpotsDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        in_email = (EditText) findViewById(R.id.edit_email);
        in_pass = (EditText) findViewById(R.id.edit_pass);
        btn_lo = (Button) findViewById(R.id.btn_login);
        btn_regis = (Button) findViewById(R.id.btn_regis);
        pd = new SpotsDialog(this,R.style.Custom);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference("User");

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterPage.class);
                startActivity(intent);
            }
        });

        btn_lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                String email = in_email.getText().toString();
                String pass = in_pass.getText().toString();
                mAuth.signInWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    mDatabase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.child("status").exists()){
                                                String Sta = dataSnapshot.child("status").getValue().toString();
                                                if(Sta.equals("admin")){
                                                    Intent intent = new Intent(MainActivity.this,Main3Activity.class);
                                                    startActivity(intent);
                                                }
                                                else if(Sta.equals("people")){
                                                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                                                    startActivity(intent);
                                                }

                                                Log.d("Login","SignInWithEmail:success");

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    pd.dismiss();
                                }
                                else{
                                    Log.w("Login","SignInwithEmail:failure");
                                    pd.dismiss();
                                }
                            }
                        });

            }
        });

    }

        @Override
        protected void onStart() {
            super.onStart();
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null){
                mDatabase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("status").exists()){
                            String Sta = dataSnapshot.child("status").getValue().toString();
                            if(Sta.equals("admin")){
                                Intent intent = new Intent(MainActivity.this,Main3Activity.class);
                                startActivity(intent);
                            }
                            else if(Sta.equals("people")){
                                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                                startActivity(intent);
                            }
                            Log.d("Login","SignInWithEmail:success");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            else {
                Log.d("Login","Not Login");
            }
        }

    }
