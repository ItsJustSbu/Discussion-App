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


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputEditText getEmail;
    TextInputEditText getPassword;
    Button Login;
    TextView noAccount;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getEmail = (TextInputEditText) findViewById(R.id.email);
        getPassword = (TextInputEditText) findViewById(R.id.password);
        Login = (Button) findViewById(R.id.buttonLogin);
        mAuth = FirebaseAuth.getInstance();
        noAccount = (TextView) findViewById(R.id.donthaveanaccount);
        progressDialog = new ProgressDialog(this);



        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Register_Activity.class));
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = getEmail.getText().toString();
                String Password = getPassword.getText().toString();
                if(TextUtils.isEmpty(Email)){
                    getEmail.setError("Email cannot be empty");
                }
                else if(TextUtils.isEmpty(Password)){
                    getPassword.setError("Password cannot be empty");
                }else{
                    signIn(Email,Password);
                }
            }
        });
    }

    public void signIn(String email, String password){
        progressDialog.setTitle("Please wait");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}