package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
         swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 Intent intent = getIntent();
                 startActivity(intent);
             }
         });

        recyclerView= (RecyclerView) findViewById(R.id.RV);
        fab = (FloatingActionButton) findViewById(R.id.CreatePost);
        fab.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<List<Data>> call = retrofitClient.getInstance().getApi().getData();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this,response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                List<Data> Datalist = response.body();
                MyAdapter myAdapter = new MyAdapter(MainActivity.this,Datalist);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.layout_dialog,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        TextInputEditText title = (TextInputEditText) mView.findViewById(R.id.etEnterTitle);
        TextInputEditText question = (TextInputEditText) mView.findViewById(R.id.etEnterResponse);
        Button create = (Button) mView.findViewById(R.id.btnRespond);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Title = title.getText().toString();
                String Question = question.getText().toString();
                Intent intent = getIntent();
                String author = intent.getStringExtra("username");
                Call<ResponseBody> call = retrofitClient.getInstance()
                        .getApi().post(author,Title,Question);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(MainActivity.this,response.code(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {
                            String s = response.body().string();
                            Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                dialog.cancel();
            }
        });

    }
}

