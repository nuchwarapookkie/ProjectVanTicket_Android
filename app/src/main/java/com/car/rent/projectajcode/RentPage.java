package com.car.rent.projectajcode;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RentPage extends Fragment {
    View view;
    Spinner startway;
    Spinner endway;
    EditText edt_cal;
    //EditText num_seat;
    RadioButton rad_btn;
    Button btn_rent;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    FirebaseDatabase wayData;
    DatabaseReference RefWay;

    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayofMonth;
    Calendar calendar;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String startt;
    String endt;
    String post_key;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_rent_page,container,false);
        startway = (Spinner) view.findViewById(R.id.spin_start_way);
        endway = (Spinner) view.findViewById(R.id.spin_end_way);
        edt_cal = (EditText) view.findViewById(R.id.calendar);
       // num_seat = (EditText) view.findViewById(R.id.number_seat);
        rad_btn = (RadioButton) view.findViewById(R.id.radio_check);
        btn_rent = (Button) view.findViewById(R.id.btn_submit_rent);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Rent");

        wayData = FirebaseDatabase.getInstance();
        RefWay = wayData.getReference("Way");

        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayofMonth = calendar.get(Calendar.DAY_OF_MONTH);

        RefWay.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> stringList1 = new ArrayList<String>();
                final List<String> stringList2 = new ArrayList<String>();
                stringList1.add(0,"ต้นทาง");
                stringList2.add(0,"ปลายทาง");
                for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                    Way sa = snapshot.getValue(Way.class);
                    stringList1.add(sa.getFinish_way());
                    stringList2.add(sa.getFinish_way());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,stringList1);
                arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,stringList2);
                arrayAdapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                startway.setAdapter(arrayAdapter);
                endway.setAdapter(arrayAdapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        startway.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                startt = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        endway.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                endt = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        edt_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                edt_cal.setText(day+"-"+(month+1)+"-"+year);
                            }
                        },year,month,dayofMonth);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        user =mAuth.getCurrentUser();
        btn_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startt.equals(endt) || startt.equals("ต้นทาง") || endt.equals("ปลายทาง")){
                    Toast.makeText(getActivity(),"เลือกต้นทางหรือปลายทางให้ถูกต้อง",Toast.LENGTH_LONG).show();
                }
                else if(!startt.equals(endt)) {
                    post_key = databaseReference.push().getKey().toString();
                    String cal = edt_cal.getText().toString();
                    if (rad_btn.isChecked()) {
                        databaseReference.child(post_key).child("date").setValue(cal);
                        databaseReference.child(post_key).child("startway").setValue(startt);
                        databaseReference.child(post_key).child("startend").setValue(endt);
                        databaseReference.child(post_key).child("user").setValue(user.getUid());
                        Intent intent = new Intent(getActivity(), time_car.class);
                        intent.putExtra("Postkey", post_key);
                        intent.putExtra("Date", cal);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Not Selected",Toast.LENGTH_LONG).show();
                }

            }
        });
        return view;
    }
}
