package com.example.firstpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class options_stu extends AppCompatActivity {
    private DrawerLayout d1;
    private ActionBarDrawerToggle abdt;
    private FirebaseAuth firebaseAuth;
    TextView txt1,txt2,txt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_stu);
        d1= (DrawerLayout)findViewById(R.id.d1);
        abdt=new ActionBarDrawerToggle(this,d1,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        d1.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view=(NavigationView)findViewById(R.id.nav_view);

        txt1=(TextView)findViewById(R.id.textView1);
        txt2=(TextView)findViewById(R.id.textView2);
        txt3=(TextView)findViewById(R.id.textView3);


        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAssignDown();


            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLectureDown();


            }
        });
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNoticeDown();


            }
        });


        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                if(id==R.id.home)
                {
                    Toast.makeText(options_stu.this,"home",Toast.LENGTH_SHORT).show();
                    openhome();
                }

                else if(id==R.id.sett)
                {
                    Toast.makeText(options_stu.this,"Settings",Toast.LENGTH_SHORT).show();
                    openSettings();
                }
                else if(id==R.id.logout)
                {
                    Toast.makeText(options_stu.this,"Logout",Toast.LENGTH_SHORT).show();
                    openLogout();
                }

                return true;
            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);


    }
    public void openAssignDown()
    {
        Intent intent1=new Intent(this,downloadass.class);
        startActivity(intent1);

    }
    public void openLectureDown()
    {
        Intent intent2=new Intent(this,downloadlec.class);
        startActivity(intent2);

    }
    public void openNoticeDown()
    {
        //Intent intent3=new Intent(this,downloadnot.class);
       // startActivity(intent3);

    }
    public void openLogout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this,LoginPage.class));

    }
    public void openSettings()
    {
        Intent intent=new Intent(this,settings.class);
        startActivity(intent);

    }
    public void openhome()
    {
        Intent intent=new Intent(this, homepage_stu.class);
        startActivity(intent);

    }
}