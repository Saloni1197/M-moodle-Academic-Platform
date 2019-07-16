package com.example.firstpage;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class uploadnot_fac extends AppCompatActivity {
    private DrawerLayout d1;
    private ActionBarDrawerToggle abdt;
    TextView txt1,txt2,txt3,txt4,txt5;
    EditText stuID;
    EditText passwd;
    TextView lst;
    TextView name;
    TextView password;
    TextView message,path;
    private static final int PICK_FILE_REQUEST= 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadnot_fac);
        d1= (DrawerLayout)findViewById(R.id.d1);
        abdt=new ActionBarDrawerToggle(this,d1,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        d1.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view=(NavigationView)findViewById(R.id.nav_view);


        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                if(id==R.id.home)
                {
                    Toast.makeText( uploadnot_fac.this,"home",Toast.LENGTH_SHORT).show();
                    openhome();
                }

                else if(id==R.id.sett)
                {
                    Toast.makeText( uploadnot_fac.this,"home",Toast.LENGTH_SHORT).show();
                    openSettings();
                }
                else if(id==R.id.logout)
                {
                    Toast.makeText( uploadnot_fac.this,"home",Toast.LENGTH_SHORT).show();
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
        Intent intent=new Intent(this,LoginPage.class);
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
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //fileUpload("untitled.txt");
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(uploadnot_fac.this, "Permission denied to read your External storage", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void chooseFile()
    {
        ActivityCompat.requestPermissions(uploadnot_fac.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);
    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    //no data present
                    return;
                } else {
                    Uri uri = data.getData();
                    message.setText(uri.getPath());
                    name.setText(getFileDataFromUri(this, uri));
                    //fileUpload(uri.getPath());
                }
            }
        }
    }

    public String getFileDataFromUri(Context context, Uri contentUri) {
        ContentResolver contentResolver = null;
        final StringBuilder stringBuilder = new StringBuilder();
        try {
            contentResolver = context.getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(contentUri);
            final int bufferSize = 1024;
            final char[] buffer = new char[bufferSize];

            Reader streamReader = new InputStreamReader(inputStream, "UTF-8");
            while (true) {
                int readLength = streamReader.read(buffer, 0, buffer.length);
                if (readLength < 0) {
                    break;
                }
                stringBuilder.append(buffer, 0, readLength);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    public void fileUpload(String filename) {
        StringBuilder text = new StringBuilder();
        try {
            File dirPath = Environment.getExternalStorageDirectory();
            File filePath = new File(filename);

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close() ;
        }catch (IOException e) {
            e.printStackTrace();
        }
        message.setText(text.toString()); //Text view to display file contents
    }

}
