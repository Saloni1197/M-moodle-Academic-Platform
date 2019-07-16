package com.example.firstpage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class options_fac extends AppCompatActivity {
    private DrawerLayout d1;
    private ActionBarDrawerToggle abdt;
    private FirebaseAuth firebaseAuth;
    TextView txt1,txt2,txt3,txt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_fac);
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
        txt4=(TextView)findViewById(R.id.textView4);


        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAssignUpload();


            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLectureUpload();


            }
        });
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNoticeUpload();


            }
        });



        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                if(id==R.id.home)
                {
                    Toast.makeText(options_fac.this,"home",Toast.LENGTH_SHORT).show();
                    openhome();
                }

                else if(id==R.id.sett)
                {
                    Toast.makeText(options_fac.this,"Settings",Toast.LENGTH_SHORT).show();
                    openSettings();
                }
                else if(id==R.id.logout)
                {
                    Toast.makeText(options_fac.this,"Logout",Toast.LENGTH_SHORT).show();
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
    public void openAssignUpload()
    {
        Intent intent1=new Intent(this,uploadass_fac.class);
        startActivity(intent1);

    }
    public void openLectureUpload()
    {
        Intent intent2=new Intent(this,uploadlec_fac.class);
        startActivity(intent2);

    }

    public void openNoticeUpload()
    {
        Intent intent3=new Intent(this,uploadnot_fac.class);
        startActivity(intent3);

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
        Intent intent=new Intent(this,homepage_fac.class);
        startActivity(intent);

    }
}