package com.example.firstpage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginPage extends AppCompatActivity {
    Button buttonf, buttons;
    EditText user, pass;
    TextView text1,text2;
    private FirebaseAuth firebaseAuth;
    ProgressBar p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  getActionBar().setTitle("");
        setContentView(R.layout.activity_login_page);
        //mydb=new DatabaseHelper(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");
        buttons = (Button) findViewById(R.id.buts);
        buttonf = (Button) findViewById(R.id.butf);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.password);
        text1 = (TextView) findViewById(R.id.textView7);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user1 = firebaseAuth.getCurrentUser();//check if the user has already logged into the app or not
        if (user1 != null) {
            startActivity(new Intent(LoginPage.this, homepage_stu.class));//if already logged in k case mein do page ko kese manage kru mein
        }
        buttonf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validateF(user.getText().toString().trim(), pass.getText().toString().trim());
                startActivity(new Intent(LoginPage.this, homepage_fac.class));

            }
        });
        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateS(user.getText().toString().trim(), pass.getText().toString().trim());

            }
        });
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword();
            }
        });

    }

    public void validateS(String userName, String userPassword) {

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    checkEmailVerificationS();

                }
                else
                    Toast.makeText(LoginPage.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
        });
    }

    public void validateF(String userName, String userPassword)
    {
        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //progressDialog.dismiss();
                    //Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    checkEmailVerificationF();
                }
                else
                    Toast.makeText(LoginPage.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
        });
    }

    private void checkEmailVerificationS()
        {
            FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
            Boolean emailflag = firebaseUser.isEmailVerified();
            if (emailflag) {
                startActivity(new Intent(this, homepage_stu.class));
            } else
                Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT);
            firebaseAuth.signOut();
        }

    private void checkEmailVerificationF()
        {
            FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
            Boolean emailflag = firebaseUser.isEmailVerified();
            if (emailflag) {
                finish();
                startActivity(new Intent(this, homepage_stu.class));
            } else
                Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
      public void forgetPassword()
      {
        startActivity(new Intent(LoginPage.this,reset_Password.class));
      }
}
