package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText username;
    private TextInputEditText password;
    private TextInputEditText confirmPassword;
    private Button buttonRegister;
    private Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username = (TextInputEditText) findViewById(R.id.etRegisterUser);
        password = (TextInputEditText) findViewById(R.id.EtRegisterpassword);
        confirmPassword = (TextInputEditText) findViewById(R.id.confirm_password);
        buttonRegister = (Button) findViewById(R.id.buttonSignUp);
        btnSignIn = (Button) findViewById(R.id.signinButton);
        buttonRegister.setOnClickListener(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this,Login.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Registration.this,Login.class);
        String uname = username.getText().toString();
        String pword = password.getText().toString();
        String confirmPword = confirmPassword.getText().toString();

            Call<ResponseBody> call = retrofitClient.getInstance()
                    .getApi().createUser(uname,pword);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if(response.body() != null){
                        try {
                            String s = response.body().string();
                            if(s.equals("Registration Successful")){
                                startActivity(intent);
                                Toast.makeText(Registration.this,"Please Sign in.",Toast.LENGTH_LONG).show();
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    else{
                        Toast.makeText(Registration.this,"Response is null",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(Registration.this, t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }




}