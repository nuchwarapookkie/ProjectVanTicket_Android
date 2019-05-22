package com.car.rent.projectajcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;


public class Main2Activity extends AppCompatActivity {
   private static final String TAG="MainActivity2";
   private SectionPageAdapter mSectionPageAdapter;
   private ViewPager mViewPages;
   private Toolbar toolbar;
   AlertDialog.Builder builder;
   FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        mAuth = FirebaseAuth.getInstance();

        mViewPages = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPages);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPages);
    }
    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomePage(),"หน้าแรก");
        adapter.addFragment(new RentPage(),"จองตั๋ว/ซื้อตั๋ว/ดูเที่ยวเวลา");
        adapter.addFragment(new ContactPage(),"ติดต่อ/สอบถาม");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("ยืนยัน");
                builder.setMessage("ต้องการออกจากระบบหรือไม่ ?");
                builder.setPositiveButton("ใช้", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        Intent intent = new Intent(getApplication(),MainActivity.class);
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
}
