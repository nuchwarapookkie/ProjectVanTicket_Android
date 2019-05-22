package com.car.rent.projectajcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class TicketPage extends AppCompatActivity {
    String datekey;
    String rentKey;
    AlertDialog.Builder builder;
    TextView name;
    TextView phone;
    TextView Seat;
    TextView start_way;
    TextView Date;
    TextView start_end;
    Button btn_edit;
    Button btn_save;
    FirebaseDatabase mFire;
    DatabaseReference mData;

    FirebaseDatabase mAuth;
    DatabaseReference mAu;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    DatabaseReference mDataTime;


    FirebaseAuth maa;
    FirebaseUser user;
    String datepost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_page);
        datekey = getIntent().getExtras().getString("Date");
        rentKey = getIntent().getExtras().getString("Rent");
        datepost = getIntent().getExtras().getString("DateKey");

        name = (TextView) findViewById(R.id.ticket_name);
        phone = (TextView) findViewById(R.id.ticket_phone);
        Seat = (TextView) findViewById(R.id.ticket_seat);
        start_way = (TextView) findViewById(R.id.ticket_start);
        Date = (TextView) findViewById(R.id.ticket_date);
        start_end =(TextView) findViewById(R.id.ticket_finish);

        btn_edit = (Button) findViewById(R.id.edit_btn);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketPage.this,RentSeat.class);
                intent.putExtra("Date",datekey);
                intent.putExtra("RentKey",rentKey);
                intent.putExtra("DateKey",datepost);
                startActivity(intent);
                finish();
            }
        });
        btn_save = (Button) findViewById(R.id.save_btn);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Ticket").push();

        maa = FirebaseAuth.getInstance();
        user = maa.getCurrentUser();

        mAuth = FirebaseDatabase.getInstance();
        mAu = mAuth.getReference("User").child(user.getUid());

        mFire = FirebaseDatabase.getInstance();
        mData = mFire.getReference("Rent").child(rentKey);

        mDataTime = FirebaseDatabase.getInstance().getReference("Time").child(datepost);


        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 final String Seat_1 = dataSnapshot.child("seat").getValue().toString();
                 final String Date_2 = dataSnapshot.child("date").getValue().toString();
                 final String start_en = dataSnapshot.child("startend").getValue().toString();
                 final String start_wa = dataSnapshot.child("startway").getValue().toString();
                 final String timest = dataSnapshot.child("time_start").getValue().toString();
                 final String timeen = dataSnapshot.child("time_end").getValue().toString();
                 mAu.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          String name_in  = dataSnapshot.child("Name").getValue().toString();
                          String phone_in = dataSnapshot.child("Phone").getValue().toString();
                          name.setText("ชือ "+name_in);
                          phone.setText("เบอร์โทรศัทพ์ "+phone_in);
                          Seat.setText("เลขที่นั่ง "+Seat_1);
                          Date.setText("วันที่เดินทาง "+Date_2+"\n เวลา "+timest+" - "+timeen);
                          start_way.setText("เริ่มต้นจาก "+start_wa);
                          start_end.setText("สิ้นสุดที่ "+start_en);

                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String Seat_1 = dataSnapshot.child("seat").getValue().toString();
                        final String Date_2 = dataSnapshot.child("date").getValue().toString();
                        final String start_en = dataSnapshot.child("startend").getValue().toString();
                        final String start_wa = dataSnapshot.child("startway").getValue().toString();
                        final String timest = dataSnapshot.child("time_start").getValue().toString();
                        final String timeen = dataSnapshot.child("time_end").getValue().toString();
                        mAu.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String name_in  = dataSnapshot.child("Name").getValue().toString();
                                String phone_in = dataSnapshot.child("Phone").getValue().toString();
                                databaseReference.child("Name").setValue(name_in);
                                databaseReference.child("Phone").setValue(phone_in);
                                databaseReference.child("Seat").setValue(Seat_1);
                                databaseReference.child("Date_Day").setValue(Date_2);
                                databaseReference.child("Start_way").setValue(start_wa);
                                databaseReference.child("Start_end").setValue(start_en);
                                databaseReference.child("time_start").setValue(timest);
                                databaseReference.child("time_end").setValue(timeen);
                                databaseReference.child("userid").setValue(user.getUid());





                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        mDataTime.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String old = dataSnapshot.child("seat_check").getValue().toString();
                                mDataTime.child("seat_check").setValue(old+Seat_1);
                                mDataTime.removeEventListener(this);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Toast.makeText(getApplicationContext(),"Rent Success !!!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplication(),Main2Activity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("ยืนยัน");
                builder.setMessage("ต้องการยกเลิกการจองหรือไม่่ ?");
                builder.setPositiveButton("ใช้", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference("Rent").child(rentKey).removeValue();
                        Intent intent = new Intent(TicketPage.this,Main2Activity.class);
                        // intent.putExtra("Postkey",post_key);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("ไม่", null);
                builder.show();
                return true;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        builder = new AlertDialog.Builder(this);
        builder.setTitle("ยืนยัน");
        builder.setMessage("ต้องการยกเลิกการจองหรือไม่่ ?");
        builder.setPositiveButton("ใช้", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseDatabase.getInstance().getReference("Rent").child(rentKey).removeValue();
                Intent intent = new Intent(TicketPage.this,Main2Activity.class);
                // intent.putExtra("Postkey",post_key);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("ไม่", null);
        builder.show();
    }
}
