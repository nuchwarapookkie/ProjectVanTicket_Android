package com.car.rent.projectajcode;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Collections;

public class admin_time extends Fragment {
    EditText edt_time;
    Spinner time_end;
    Spinner time_start;
    Button btn_submit;
    View view;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String timestart;
    String timeend;
    DatePickerDialog datePickerDialog;

    FirebaseDatabase firebaseDatabase;
    Query mData;
    int year;
    int month;
    int dayofMonth;
    Calendar calendar;
    int Y;
    int M;
    int D;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_admin_time,container,false);


        edt_time = (EditText) view.findViewById(R.id.edt_calendar);
        time_end = (Spinner) view.findViewById(R.id.time_end_spin);
        time_start = (Spinner) view.findViewById(R.id.time_start_spin);
        btn_submit = (Button) view.findViewById(R.id.save_way_time);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.time,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_end.setAdapter(adapter);
        time_start.setAdapter(adapter);
        time_start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                timestart = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        time_end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                timeend = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_time);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mlinearLayout =new LinearLayoutManager(getActivity());
        mlinearLayout.setReverseLayout(true);
        mlinearLayout.setStackFromEnd(true);
        recyclerView.setLayoutManager(mlinearLayout);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
        edt_time.setText(dayofMonth+"-"+(month+1)+"-"+year);

        edt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                edt_time.setText(day+"-"+(month+1)+"-"+year);
                                Y = year;
                                M = month;
                                D = day;
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.detach(admin_time.this).attach(admin_time.this).commit();
                            }
                        },year,month,dayofMonth);

                datePickerDialog.show();

            }
        });
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Time");






        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String date = edt_time.getText().toString();

                if(timestart.equals("") || timeend.equals("") || date.equals("")){
                    Toast.makeText(getActivity(),"No data",Toast.LENGTH_LONG).show();
                }
                else {
                    String key = databaseReference.push().getKey();
                    databaseReference.child(key).child("time_end").setValue(timeend);
                    databaseReference.child(key).child("time_start").setValue(timestart);
                    databaseReference.child(key).child("date").setValue(date);
                    databaseReference.child(key).child("seat_check").setValue("");

                }
            }
        });


        return view;
    }
    @Override
    public void onStart() {
        super.onStart();


        String Date = edt_time.getText().toString();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mData = firebaseDatabase.getReference("Time").orderByChild("date").equalTo(Date);

        FirebaseRecyclerOptions<Card_car> options = new FirebaseRecyclerOptions.Builder<Card_car>()
                .setQuery(mData,Card_car.class)
                .build();

        FirebaseRecyclerAdapter<Card_car,Card_View> adapter = new FirebaseRecyclerAdapter<Card_car, Card_View>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final Card_View holder, int position, @NonNull Card_car model) {
               final String post_key = getRef(position).getKey();
               holder.setTimeStart(model.getTime_start());
               holder.setTimeEnd(model.getTime_end());
               holder.stack.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       PopupMenu popupMenu = new PopupMenu(getActivity(),holder.stack);
                       popupMenu.inflate(R.menu.menu_setting);
                       popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                           @Override
                           public boolean onMenuItemClick(MenuItem menuItem) {
                               switch (menuItem.getItemId()){
                                   case R.id.delete:
                                       FirebaseDatabase.getInstance().getReference("Time").child(post_key).removeValue();
                               }
                               return false;
                           }
                       });
                       popupMenu.show();
                   }
               });
               holder.setDate(model.getDate());

            }

            @NonNull
            @Override
            public Card_View onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.content_time_admin,viewGroup,false);
                return new Card_View(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public static class Card_View extends RecyclerView.ViewHolder{
        View mView;
        ImageView stack;
        public Card_View(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            stack = (ImageView) mView.findViewById(R.id.img_stack);
        }
        public void setTimeStart(String start){
            TextView startTime = (TextView) mView.findViewById(R.id.time_start_admin_card);
            startTime.setText(start);
        }
        public void setTimeEnd(String end){
            TextView endTime = (TextView) mView.findViewById(R.id.time_end_admin_card);
            endTime.setText(end);
        }
        public void setDate(String date){
            TextView textView = (TextView) mView.findViewById(R.id.text_date);
            textView.setText(date);
        }
    }
}
