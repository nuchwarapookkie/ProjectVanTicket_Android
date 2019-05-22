package com.car.rent.projectajcode;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_way extends Fragment {

    View view;
    EditText way;
    Button save;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_admin_way,container,false);
        way = (EditText) view.findViewById(R.id.admin_way);
        save = (Button) view.findViewById(R.id.save_way);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Way");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_way);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Way =way.getText().toString();
                if (Way.equals("")){
                    Toast.makeText(getActivity(),"No Data",Toast.LENGTH_LONG).show();
                }
                else if(!Way.equals("")){
                    databaseReference.push().child("finish_way").setValue(Way);
                    Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG).show();
                }


            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Card_car> options = new FirebaseRecyclerOptions.Builder<Card_car>()
                .setQuery(databaseReference,Card_car.class)
                .build();

        final FirebaseRecyclerAdapter<Card_car,Card_View> adapter = new FirebaseRecyclerAdapter<Card_car, Card_View>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final Card_View holder, int position, @NonNull Card_car model) {
                final String post_key = getRef(position).getKey();

                holder.setWay(model.getFinish_way());

                holder.stack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(getActivity(),holder.stack);
                        popupMenu.inflate(R.menu.menu_setting);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()) {
                                    case R.id.delete:
                                        FirebaseDatabase.getInstance().getReference("Way").child(post_key).removeValue();
                                        break;
                                }
                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });
            }

            @NonNull
            @Override
            public Card_View onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.content_way,viewGroup,false);
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
            stack = (ImageView) mView.findViewById(R.id.img_stack_2);
        }
        public void setWay(String way){
            TextView Way = (TextView) mView.findViewById(R.id.finish_way);
            Way.setText(way);
        }
    }
}
