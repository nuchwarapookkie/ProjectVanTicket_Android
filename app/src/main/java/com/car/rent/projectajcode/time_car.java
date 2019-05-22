package com.car.rent.projectajcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class time_car extends AppCompatActivity {
    RecyclerView recyclerView;
    Query mData;
    FirebaseDatabase firebaseDatabase;
    AlertDialog.Builder builder;
    String post_key2;
    String Date;
    TextView textView;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_car);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        post_key2 = getIntent().getExtras().getString("Postkey");
        Date = getIntent().getExtras().getString("Date");

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_time_car);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mlinearLayout =new LinearLayoutManager(this);
        mlinearLayout.setReverseLayout(true);
        mlinearLayout.setStackFromEnd(true);
        recyclerView.setLayoutManager(mlinearLayout);


        textView = (TextView) findViewById(R.id.text_time_way);
        textView.setText("วันทีเดินทาง "+Date);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference("Time").orderByChild("date").equalTo(Date);

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
                        FirebaseDatabase.getInstance().getReference("Rent").child(post_key2).removeValue();
                        Intent intent = new Intent(time_car.this,Main2Activity.class);
                        // intent.putExtra("Postkey",post_key);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("ไม่", null);
                builder.show();
             break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Card_car> options =  new FirebaseRecyclerOptions.Builder<Card_car>()
                .setQuery(mData,Card_car.class)
                .build();
        FirebaseRecyclerAdapter<Card_car,Card_View> adapter = new FirebaseRecyclerAdapter<Card_car, Card_View>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final Card_View holder, int position, @NonNull final Card_car model) {
                final String post_key = getRef(position).getKey();
                String check_S = model.getSeat_check();
               try {
                   if(check_S.contains("S1") && check_S.contains("S2") && check_S.contains("S3")&& check_S.contains("S4") && check_S.contains("S5")
                           && check_S.contains("S6") && check_S.contains("S7")&& check_S.contains("S8") && check_S.contains("S9") && check_S.contains("S10")
                           && check_S.contains("S11") && check_S.contains("S12") && check_S.contains("S13")){

                       holder.cardView.setCardBackgroundColor(Color.RED);

                       holder.cardView.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Toast.makeText(getApplication(),"Full Seat !",Toast.LENGTH_LONG).show();
                           }
                       });


                   }
                   else {
                       holder.cardView.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Intent intent = new Intent(getApplication(), RentSeat.class);
                               FirebaseDatabase.getInstance().getReference("Rent").child(post_key2).child("dateID").setValue(post_key);
                               FirebaseDatabase.getInstance().getReference("Rent").child(post_key2).child("date").setValue(Date);
                               FirebaseDatabase.getInstance().getReference("Rent").child(post_key2).child("time_start").setValue(model.getTime_start());
                               FirebaseDatabase.getInstance().getReference("Rent").child(post_key2).child("time_end").setValue(model.getTime_end());
                               intent.putExtra("Date", Date);
                               intent.putExtra("DateKey", post_key);
                               intent.putExtra("RentKey", post_key2);
                               startActivity(intent);
                               finish();
                           }
                       });


                   }
                   holder.setStartTime(model.getTime_start());
                   holder.setEndTime(model.getTime_end());

               }catch (Exception e){
                   e.getMessage();
               }
            }

            @NonNull
            @Override
            public Card_View onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.content_time,viewGroup,false);
                return new Card_View(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    public static class Card_View extends RecyclerView.ViewHolder{
        View mView;
        CardView cardView;
        LinearLayout card_linear;
        TextView stime;
        TextView etime;
        public Card_View(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            card_linear = (LinearLayout) mView.findViewById(R.id.liner_background);
            cardView = (CardView) mView.findViewById(R.id.card_view_time);
        }
        public void setStartTime(String startt){
            stime =(TextView) mView.findViewById(R.id.text_time_start);
            stime.setText(startt);
        }
        public void setEndTime(String endt){
            etime = (TextView) mView.findViewById(R.id.text_time_end);
            etime.setText(endt);
        }
    }

}
