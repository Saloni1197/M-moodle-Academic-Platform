package com.example.firstpage;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class uploadass_fac extends AppCompatActivity {
    private DrawerLayout d1;
    private ActionBarDrawerToggle abdt;
    EditText title,about;
    Button selectfile,upload;
    Uri pdfUri; //uri are actually urls menat for local storage
    TextView notification;
    private static final int PICK_FILE_REQUEST= 1000;
    FirebaseStorage storage; //used for uploading files
    FirebaseDatabase database; //used to store the urls of the uploaded file
   //ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadass_fac);
        d1= (DrawerLayout)findViewById(R.id.d1);
        abdt=new ActionBarDrawerToggle(this,d1,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        d1.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view=(NavigationView)findViewById(R.id.nav_view);
        selectfile = (Button) findViewById(R.id.button6);
        upload = (Button) findViewById(R.id.button5);
         title = (EditText) findViewById(R.id.editText5);
         about = (EditText) findViewById(R.id.editText6);
         notification=(TextView)findViewById(R.id.not);
        database= FirebaseDatabase.getInstance(); //return the object of firebase database
        storage=FirebaseStorage.getInstance();//return the object of the firebase storage


        selectfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print("hello");
                //you must have permisiion to read the internal storage of your phone
                if(ContextCompat.checkSelfPermission(uploadass_fac.this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
                {
                    selectpdf();
                }
                else
                {
                    //we will ask the user to grant the permission
                    ActivityCompat.requestPermissions(uploadass_fac.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);

                }

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfUri!=null) //means user have successfully selected the file
                {
                    uploadFile(pdfUri);
                }
                else
                    Toast.makeText(uploadass_fac.this,"Please select the file",Toast.LENGTH_SHORT);
            }
        });
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                if(id==R.id.home)
                {
                    Toast.makeText( uploadass_fac.this,"home",Toast.LENGTH_SHORT).show();
                    openhome();
                }

                else if(id==R.id.sett)
                {
                    Toast.makeText( uploadass_fac.this,"home",Toast.LENGTH_SHORT).show();
                    openSettings();
                }
                else if(id==R.id.logout)
                {
                    Toast.makeText( uploadass_fac.this,"home",Toast.LENGTH_SHORT).show();
                    openLogout();
                }

                return true;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) //we have asked for onlly one request and that request will be stored at the 0 index of the arary
    {
        if((requestCode==9) && grantResults[0]==PackageManager.PERMISSION_GRANTED)//to check whether the integer arary has the value of permission granted or not
        {
            selectpdf();
        }
        else
        {
            Toast.makeText(uploadass_fac.this,"Please provide permission..",Toast.LENGTH_SHORT);//for 1 second in case of length short and 3 second for length long
        }
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    public void selectpdf()
    {
        Intent intent =new Intent();
        intent.setType("\"*/*\""); //select all types of the files
        intent.setAction(Intent.ACTION_GET_CONTENT); //to fetch files
        startActivityForResult(intent,86);//onActivityResult ,method will get automatically invoked by android
    }
    public void uploadFile(Uri pdfUri)
    {
       /* progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading the file");
        progressDialog.setProgress(0);
        progressDialog.show();*/

        final String filename =System.currentTimeMillis()+"";
        StorageReference storageReference=storage.getReference(); //returns the root path where the file is stored
        storageReference.child("uploaded assignments").child(filename).putFile(this.pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url =taskSnapshot.getMetadata().getReference().getDownloadUrl().toString().toString();  //if our file is successfully uploaded in the firebase we can get the url of the file by using this task
                    //store the url in relative database

                DatabaseReference databaseReference=database.getReference(); //return the pathnto the root
                databaseReference.child(filename).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(uploadass_fac.this,"File successully uploaded",Toast.LENGTH_SHORT);
                        }
                        else
                            Toast.makeText(uploadass_fac.this,"File not successully uploaded",Toast.LENGTH_SHORT);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(uploadass_fac.this,"File not successully uploaded",Toast.LENGTH_SHORT);
            }
        });
            /*
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Overridepublic void onProgress(UploadTask.TaskSnapshot taskSnapshot)
            {
                //track the progress of our upload
                //int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                //progressDialog.setProgress(currentProgress);
            }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //to check whether the user has successfully chosen the file
        if(requestCode==86 && resultCode==RESULT_OK && data!= null)//result ok checks whther the operation is completed or not and the third one is to check whether the user has selected the dile or not
        {
            pdfUri=data.getData(); //return the uri of the selected file
        }
        else
        {
            Toast.makeText(uploadass_fac.this,"Please select the file",Toast.LENGTH_SHORT);
        }
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


}
