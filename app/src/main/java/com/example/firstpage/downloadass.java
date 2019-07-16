package com.example.firstpage;


import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.Context;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.FileNameMap;

public class downloadass extends AppCompatActivity {
    private DrawerLayout d1;
    private ActionBarDrawerToggle abdt;
    TextView txt1,txt2,txt3,txt4,txt5;
    private FirebaseAuth firebaseAuth;
    Button download;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadass);
        d1= (DrawerLayout)findViewById(R.id.d1);
        abdt=new ActionBarDrawerToggle(this,d1,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        d1.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view=(NavigationView)findViewById(R.id.nav_view);
        download=(Button)findViewById(R.id.button5);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                download();
            }
        });

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                if(id==R.id.home)
                {
                    Toast.makeText( downloadass.this,"home",Toast.LENGTH_SHORT).show();
                    openhome();
                }

                else if(id==R.id.sett)
                {
                    Toast.makeText( downloadass.this,"settings",Toast.LENGTH_SHORT).show();
                    openSettings();
                }
                else if(id==R.id.logout)
                {
                    Toast.makeText( downloadass.this,"logout",Toast.LENGTH_SHORT).show();
                    openLogout();
                }

                return true;
            }
        });
    }
    public void download()
        {
            storageReference=firebaseStorage.getInstance().getReference();
            storageReference.child("uploaded assignments").child("");
             ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                 @Override
                 public void onSuccess(Uri uri) {
                        //downloadfiles();
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {

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