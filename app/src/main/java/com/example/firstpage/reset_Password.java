package com.example.firstpage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class reset_Password extends AppCompatActivity {
private EditText email;
private Button resetPassword;
private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__password);

        email=(EditText)findViewById(R.id.editText1);
        resetPassword=(Button)findViewById(R.id.button2);
        firebaseAuth=FirebaseAuth.getInstance();
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1=email.getText().toString().trim();

                if(email1.equals(""))
                {
                    Toast.makeText(reset_Password.this,"Please enter your registered email ID",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    firebaseAuth.sendPasswordResetEmail(email1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(reset_Password.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(reset_Password.this, LoginPage.class));
                                }
                                else
                                    {
                                        Toast.makeText(reset_Password.this, "Error in sending password reset mail", Toast.LENGTH_SHORT).show();
                                    }
                        }
                    });
                }
            }
        });
    }
}

