package com.example.App2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_Activity extends AppCompatActivity {
        FirebaseAuth mAuth;
        TextInputEditText regEmail;
        TextInputEditText regPassword;
        TextInputEditText cPassword;
        Button regButton;
        TextView AHA;
        ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regEmail = (TextInputEditText) findViewById(R.id.registerEmail);
        regPassword = (TextInputEditText) findViewById(R.id.registerPassword);
        cPassword = (TextInputEditText) findViewById(R.id.confrimPassword);
        regButton = (Button) findViewById(R.id.registerButton);
        AHA = (TextView) findViewById(R.id.alreadyHaveanAccount);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        AHA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register_Activity.this,MainActivity.class);
                startActivity(i);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = regEmail.getText().toString().trim();
                String Password = regPassword.getText().toString().trim();
                String Confirm = cPassword.getText().toString().trim();
                if(TextUtils.isEmpty(Email)){
                    regEmail.setError("Email cannot be empty");
                }
                else if(TextUtils.isEmpty(Password)){
                    regPassword.setError("Password cannot be empty");
                }
                else if(!Password.equals(Confirm)){
                    cPassword.setError("Passwords do not match");
                }
                else{
                    registerUser(Email,Password);
                }

            }
        });
    }

    void registerUser(String email, String password) {
        progressDialog.setTitle("Please wait");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(Register_Activity.this,MainActivity.class));
                            Toast.makeText(Register_Activity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(Register_Activity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Register_Activity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }
}