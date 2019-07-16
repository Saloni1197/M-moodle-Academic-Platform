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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class homepage_fac extends AppCompatActivity {
private DrawerLayout d1;
private ActionBarDrawerToggle abdt;
TextView txt1,txt2,txt3,txt4,txt5;
private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_fac);
        d1= (DrawerLayout)findViewById(R.id.d1);
        abdt=new ActionBarDrawerToggle(this,d1,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        d1.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view=(NavigationView)findViewById(R.id.nav_view);

                txt1=(TextView)findViewById(R.id.auth1);
                txt2=(TextView)findViewById(R.id.auth2);
                txt3=(TextView)findViewById(R.id.auth3);
                txt4=(TextView)findViewById(R.id.auth4);
                txt5=(TextView)findViewById(R.id.auth5);

                txt1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                openOptions_fac();


                                            }
                                        });
                txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                         openOptions_fac();


            }
        });
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOptions_fac();


            }
        }); txt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOptions_fac();


            }
        }); txt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOptions_fac();


            }
        });



        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                if(id==R.id.home)
                {
                    Toast.makeText(homepage_fac.this,"Home",Toast.LENGTH_SHORT).show();
                    openhome();
                }
                else if(id==R.id.sett)
                {
                    Toast.makeText(homepage_fac.this,"Settings",Toast.LENGTH_SHORT).show();
                    openSettings();
                }
                else if(id==R.id.adds)
                {
                    Toast.makeText(homepage_fac.this,"Add Student",Toast.LENGTH_SHORT).show();
                    openAddStudent();
                }
                else
                {
                    Toast.makeText(homepage_fac.this,"Logout",Toast.LENGTH_SHORT).show();
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
    public void openLogout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(homepage_fac.this,LoginPage.class));

    }
    public void openAddStudent()
    {
        Intent intent=new Intent(this,register_fac.class);
        startActivity(intent);

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
    public void openOptions_fac()
    {
        Intent intent=new Intent(this,options_fac.class);
        startActivity(intent);
    }

}
