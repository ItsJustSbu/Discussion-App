package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText username;
    private TextInputEditText password;
    private Button buttonSignin;
    private Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (TextInputEditText) findViewById(R.id.EtUserName);
        password = (TextInputEditText) findViewById(R.id.EtPassword);
        buttonSignin = (Button) findViewById(R.id.btnSignIn);
        buttonSignUp = (Button) findViewById(R.id.btnsignup);
        buttonSignin.setOnClickListener(this);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Registration.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        String uname = username.getText().toString();
        String pword = password.getText().toString();
        Intent intent = new Intent(Login.this,MainActivity.class);
        intent.putExtra("username",uname);
        Call<ResponseBody> call = retrofitClient.getInstance()
                .getApi().logUser(uname,pword);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.body() != null){
                    try {
                        String s = response.body().string();
                        if(s.equals("1")){

                            Toast.makeText(Login.this,"Welcome "+uname,Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                       }
                       else{
                            Toast.makeText(Login.this,"enter details again or Register",Toast.LENGTH_LONG).show();
                      }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                else{
                    Toast.makeText(Login.this,"Response is null",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Login.this, "Error my bruh",Toast.LENGTH_LONG).show();
            }
        });
    }
}