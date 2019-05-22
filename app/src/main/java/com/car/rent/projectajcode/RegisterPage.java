package com.car.rent.projectajcode;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class RegisterPage extends AppCompatActivity {
     EditText name;
     EditText email;
     EditText card;
     EditText address;
     EditText phone;
     EditText re_pass;
     EditText pass;
     Button btn_submit;
     FirebaseAuth mAuth;
     FirebaseDatabase database;
     DatabaseReference ref;
     SpotsDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        name = (EditText) findViewById(R.id.Name_regis);
        email = (EditText) findViewById(R.id.email_regis);
        card = (EditText) findViewById(R.id.card_id_regis);
        address = (EditText) findViewById(R.id.address_regis);
        phone = (EditText) findViewById(R.id.phone_regis);
        re_pass = (EditText) findViewById(R.id.re_pass_regis);
        pass = (EditText) findViewById(R.id.pass_regis);
        pd = new SpotsDialog(this,R.style.Custom);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("User");

        btn_submit =(Button) findViewById(R.id.btn_regis_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                final String name_re = name.getText().toString();
                final String email_re = email.getText().toString();
                final String card_re = card.getText().toString();
                final String address_re = address.getText().toString();
                final String phone_re = phone.getText().toString();
                String re_pass_re = re_pass.getText().toString();
                String pass_re = pass.getText().toString();
                if(!pass_re.equals(re_pass_re)){
                    Toast.makeText(getApplication(),"Password not match ",Toast.LENGTH_LONG).show();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email_re,pass_re)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                   ref.addValueEventListener(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                           final FirebaseUser user = mAuth.getCurrentUser();
                                           ref.child(user.getUid()).child("Name").setValue(name_re);
                                           ref.child(user.getUid()).child("Email").setValue(email_re);
                                           ref.child(user.getUid()).child("cardID").setValue(card_re);
                                           ref.child(user.getUid()).child("address").setValue(address_re);
                                           ref.child(user.getUid()).child("Phone").setValue(phone_re);
                                           ref.child(user.getUid()).child("status").setValue("people");
                                       }

                                       @Override
                                       public void onCancelled(@NonNull DatabaseError databaseError) {

                                       }
                                   });
                                   pd.dismiss();
                                   Toast.makeText(getApplication(),"Success !!",Toast.LENGTH_LONG).show();
                                   Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                                   startActivity(intent);
                                   finish();
                                }
                            });
                }

            }
        });
    }
}
