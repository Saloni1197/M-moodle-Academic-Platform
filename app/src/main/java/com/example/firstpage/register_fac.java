package com.example.firstpage;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.ui.phone.CountryListSpinner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register_fac extends AppCompatActivity {
    EditText stuFirstName,stuLastName,stuEnrollNo,stuYear,stuPass,stuConPass,stuEmail;
    Button reg;
    TextView txt1,txt2,txt3,txt4,txt5;
    private StudentLoginInfo stdntloginInfo=new StudentLoginInfo();
    FirebaseDatabase database;
    DatabaseReference ref;
    private DrawerLayout d1;
    private ActionBarDrawerToggle abdt;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setContentView(R.layout.activity_register_fac);
        stuFirstName =(EditText)findViewById(R.id.editText);
        stuLastName =(EditText)findViewById(R.id.editText2);
        stuEnrollNo =(EditText)findViewById(R.id.editText3);
        stuEmail =(EditText)findViewById(R.id.editText6);
        stuYear =(EditText)findViewById(R.id.editText4);
        stuPass =(EditText)findViewById(R.id.editText7);
        stuConPass =(EditText)findViewById(R.id.editText8);
        reg=(Button)findViewById(R.id.button);
        database=FirebaseDatabase.getInstance();
        ref=database.getReference().child("Student");
        firebaseAuth=FirebaseAuth.getInstance();
        d1= (DrawerLayout)findViewById(R.id.d1);
        abdt=new ActionBarDrawerToggle(this,d1,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        d1.addDrawerListener(abdt);
        abdt.syncState();
        NavigationView nav_view=(NavigationView)findViewById(R.id.nav_view);

        txt1=(TextView)findViewById(R.id.textView11);
        txt2=(TextView)findViewById(R.id.textView2);
        txt3=(TextView)findViewById(R.id.textView3);


        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAssignDown();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                {
                    String stupass1 = stuPass.getText().toString().trim();
                    String stuEmail1 = stuEmail.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(stuEmail1, stupass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                sentEmailVerification();
                            }
                            else
                                Toast.makeText(register_fac.this, "Registeration failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }



            private boolean validate() {
                Boolean result = false;
                String fname = stuFirstName.getText().toString();
                String lname = stuLastName.getText().toString();
                String rollNo = stuEnrollNo.getText().toString();
                String year = stuYear.getText().toString();
                String npass = stuPass.getText().toString();
                String cpass = stuConPass.getText().toString();
                if (fname.isEmpty() || lname.isEmpty() || rollNo.isEmpty() || year.isEmpty() || npass.isEmpty() || cpass.isEmpty()) {
                    Toast.makeText(register_fac.this, "Please enter all the details ", Toast.LENGTH_SHORT).show();
                } else if (!cpass.equals(npass)) {
                    Toast.makeText(register_fac.this, "Password should match with the confirm password ", Toast.LENGTH_SHORT).show();
                } else
                    result = true;
                return result;
            }
        });

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                if(id==R.id.home)
                {
                    Toast.makeText(register_fac.this,"home",Toast.LENGTH_SHORT).show();
                    openhome();
                }

                else if(id==R.id.sett)
                {
                    Toast.makeText(register_fac.this,"Settings",Toast.LENGTH_SHORT).show();
                    openSettings();
                }
                else if(id==R.id.logout)
                {
                    Toast.makeText(register_fac.this,"Logout",Toast.LENGTH_SHORT).show();
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
/*        Intent intent3=new Intent(this,downloadnot.class);
        startActivity(intent3);*/

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
    private void sentEmailVerification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(register_fac.this, "Successfully registered ,Verification  email has been sent!", Toast.LENGTH_SHORT).show();
                        openDialog();
                    }
                    else {
                        Toast.makeText(register_fac.this, "Verification mail hasn't be sent! ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void openDialog() {
        ExampleDialo ex = new ExampleDialo();
        ex.show(getSupportFragmentManager(), "example dialog");
    }


}
