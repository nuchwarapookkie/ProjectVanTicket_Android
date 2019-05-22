package com.car.rent.projectajcode;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class RentSeat extends AppCompatActivity {
    String post_key;
    String rentKey;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mData;
    CheckBox S1,S2,S3,S4,S5,S6,S7,S8,S9,S10,S11,S12,S13;
    ArrayList<String> cars = new ArrayList<String>();
    HashSet hs = new HashSet();
    String str = "";
    Button btn_submit;
    TextView plan;
    TextView start_plan;
    TextView start_date;
    TextView end_way;
    String datepost;

    DatabaseReference mDatatime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_seat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        post_key = getIntent().getExtras().getString("Date");
        rentKey = getIntent().getExtras().getString("RentKey");
        datepost = getIntent().getExtras().getString("DateKey");

        plan = (TextView) findViewById(R.id.way_car);
        start_plan = (TextView) findViewById(R.id.way_com_car);
        start_date = (TextView) findViewById(R.id.way_start_car);
        end_way = (TextView) findViewById(R.id.way_finish_car);

        S1 = (CheckBox) findViewById(R.id.radio_s1);
        S2 = (CheckBox) findViewById(R.id.radio_s2);
        S3 = (CheckBox) findViewById(R.id.radio_s3);
        S4 = (CheckBox) findViewById(R.id.radio_s4);
        S5 = (CheckBox) findViewById(R.id.radio_s5);
        S6 = (CheckBox) findViewById(R.id.radio_s6);
        S7 = (CheckBox) findViewById(R.id.radio_s7);
        S8 = (CheckBox) findViewById(R.id.radio_s8);
        S9 = (CheckBox) findViewById(R.id.radio_s9);
        S10 = (CheckBox) findViewById(R.id.radio_s10);
        S11 = (CheckBox) findViewById(R.id.radio_s11);
        S12 = (CheckBox) findViewById(R.id.radio_s12);
        S13 = (CheckBox) findViewById(R.id.radio_s13);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference("Rent").child(rentKey);

        mDatatime = FirebaseDatabase.getInstance().getReference("Time").child(datepost);
        mDatatime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              if (dataSnapshot.exists()){
                String s_check = dataSnapshot.child("seat_check").getValue().toString();
                if(s_check.contains("S1")){
                     S1.setEnabled(false);
                }
                if(s_check.contains("S2")){
                    S2.setEnabled(false);
                }
                if(s_check.contains("S3")){
                    S3.setEnabled(false);
                }
                if(s_check.contains("S4")){
                    S4.setEnabled(false);
                }
                if(s_check.contains("S5")){
                    S5.setEnabled(false);

                }if(s_check.contains("S6")){
                    S6.setEnabled(false);
                }
                if(s_check.contains("S7")){
                    S7.setEnabled(false);
                }
                if(s_check.contains("S8")){
                    S8.setEnabled(false);
                }
                if(s_check.contains("S9")){
                    S9.setEnabled(false);
                }
                if(s_check.contains("S10")){
                    S10.setEnabled(false);
                }
                if(s_check.contains("S11")){
                    S11.setEnabled(false);
                }
                if(s_check.contains("S12")){
                    S12.setEnabled(false);
                }
                if(s_check.contains("S13")) {
                    S13.setEnabled(false);
                }
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              try {
                  final String Date_2 = dataSnapshot.child("date").getValue().toString();
                  final String start_en = dataSnapshot.child("startend").getValue().toString();
                  final String start_wa = dataSnapshot.child("startway").getValue().toString();
                  final String time_start = dataSnapshot.child("time_start").getValue().toString();
                  final String time_end = dataSnapshot.child("time_end").getValue().toString();
                  plan.setText("เส้นทาง " + start_wa + " - " + start_en);
                  start_plan.setText("เดินทางจาก " + start_wa);
                  start_date.setText("วันที่เดินทาง " + Date_2 + "\nเวลา " + time_start + " - " + time_end);
                  end_way.setText("ถึง " + start_en);
              }catch (Exception e){
                  e.getMessage();
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_submit = (Button) findViewById(R.id.btn_okey);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(S1.isChecked()){
                    cars.add("S1");
                }
                if(S2.isChecked()){
                    cars.add("S2");
                }
                if(S3.isChecked()){
                    cars.add("S3");
                }
                if(S4.isChecked()){
                    cars.add("S4");
                }
                if(S5.isChecked()){
                    cars.add("S5");
                }
                if(S6.isChecked()){
                    cars.add("S6");
                }
                if(S7.isChecked()){
                    cars.add("S7");
                }
                if(S8.isChecked()){
                    cars.add("S8");
                }
                if(S9.isChecked()){
                    cars.add("S9");
                }
                if(S10.isChecked()){
                    cars.add("S10");
                }
                if(S11.isChecked()){
                    cars.add("S11");
                }
                if(S12.isChecked()){
                    cars.add("S12");
                }
                if(S13.isChecked()){
                    cars.add("S13");
                }
                for(String seat:cars){
                    str += seat+",";
                }
                mData.child("seat").setValue(str);
                Intent intent = new Intent(getApplication(),TicketPage.class);
                intent.putExtra("Rent",rentKey);
                intent.putExtra("Date",post_key);
                intent.putExtra("DateKey",datepost);
                startActivity(intent);
                finish();
            }
        });

       /* createListener(S1);
        createListener(S2);
        createListener(S3);
        createListener(S4);
        createListener(S5);
        createListener(S6);
        createListener(S7);
        createListener(S8);
        createListener(S9);
        createListener(S10);
        createListener(S11);
        createListener(S12);
        createListener(S13);*/



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RentSeat.this,time_car.class);
        intent.putExtra("Date",post_key);
        intent.putExtra("Postkey",rentKey);
        startActivity(intent);
        finish();
    }
   /* public void createListener(final CheckBox checkBox){
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    checkboxname = ((CheckBox) view).getText().toString();
                }
                else if(((CheckBox) view).isChecked() == false){

                }


            }
        });
    }*/
}
