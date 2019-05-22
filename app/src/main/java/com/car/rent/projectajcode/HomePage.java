package com.car.rent.projectajcode;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

public class HomePage extends Fragment {
    View view;
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    Query databaseReference;
    Query mDataTime;
    TextView total_money;
    long total = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home_page,container,false);

        total_money = (TextView) view.findViewById(R.id.total_Price);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAuth =FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Ticket").orderByChild("userid").equalTo(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String seat_total = "";
               // total = dataSnapshot.getChildrenCount()*50;
                for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                    Card_home sa = snapshot.getValue(Card_home.class);
                    String seat = sa.getSeat();
                    seat_total += seat;
                    Log.d("Yo",seat);

                    String[] s_splite = seat_total.split(",");
                   /* for (String Seat:s_splite){

                    }*/
                    total_money.setText("รวม \t\t"+String.valueOf(s_splite.length*50)+" บาท");
                }



              //  total_money.setText(String.valueOf(total)+" บาท");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDataTime = FirebaseDatabase.getInstance().getReference("Time");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Card_home> options = new FirebaseRecyclerOptions.Builder<Card_home>()
                .setQuery(databaseReference,Card_home.class)
                .build();

        FirebaseRecyclerAdapter<Card_home,Card_View> adapter = new FirebaseRecyclerAdapter<Card_home,Card_View>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final Card_View holder, int position, @NonNull Card_home model) {
                final String post_key = getRef(position).getKey();
                holder.setName1(model.getName());
                holder.setPhone1(model.getPhone());
                holder.stak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(getActivity(),holder.stak);
                        popupMenu.inflate(R.menu.menu_setting);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()){
                                    case R.id.delete:
                                        FirebaseDatabase.getInstance().getReference("Ticket").child(post_key).removeValue();
                                }
                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });
                holder.setDate1(model.getDate_Day(),model.getTime_start(),model.getTime_end());
                holder.setSeat1(model.getSeat());
                holder.setStart1(model.getTime_start());
                holder.setEnd1(model.getTime_end());


            }

            @NonNull
            @Override
            public Card_View onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.content_card,viewGroup,false);
                return new Card_View(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public static class Card_View extends RecyclerView.ViewHolder{
        View mView;
        ImageView stak;
        public Card_View(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            stak = (ImageView) mView.findViewById(R.id.stack_card);
        }
        public void setName1(String name)
        {
            TextView namehome = (TextView) mView.findViewById(R.id.ticket_name_hone);
            namehome.setText("ชือ "+name);

        }
        public void setPhone1(String phone){
            TextView phonehome = (TextView) mView.findViewById(R.id.ticket_phone_home);
            phonehome.setText("เบอร์โทรศัทพ์ "+phone);
        }
        public void setSeat1(String seat){
            TextView seathome =( TextView) mView.findViewById(R.id.ticket_seat_home);
            seathome.setText("เลขที่นั่ง "+seat);
        }
        public void setStart1(String wayStart){
            TextView starthome = (TextView) mView.findViewById(R.id.ticket_start_home);
            starthome.setText("เริ่มต้นจาก "+wayStart);
        }
        public void setEnd1(String wayEnd){
            TextView endhome = (TextView) mView.findViewById(R.id.ticket_finish_home);
            endhome.setText("สิ้นสุดที่ "+wayEnd);
        }
       public void setDate1(String date,String times,String timee){
            TextView Date = (TextView) mView.findViewById(R.id.ticket_date_home);
            Date.setText("วันที่เดินทาง "+date+"\nเวลา "+times+" - "+timee);
        }
    }
}
