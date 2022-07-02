package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class displayQuestions extends AppCompatActivity implements View.OnClickListener {

    RecyclerView responseRecycler;
    FloatingActionButton rB;
    TextView displayT;
    TextView displayQ;
    String author;
    String title;
    String question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_questions);
        responseRecycler= (RecyclerView) findViewById(R.id.responseRecyclerView);

        responseRecycler.setLayoutManager(new LinearLayoutManager(this));
        rB = (FloatingActionButton) findViewById(R.id.responseButton);
        rB.setOnClickListener(this);

        Intent intent = getIntent();
        author = intent.getStringExtra("Author");
        title = intent.getStringExtra("Title");
        question = intent.getStringExtra("Question");

        displayT = (TextView) findViewById(R.id.DisplayTItle);
        displayQ = (TextView) findViewById(R.id.displayQuestion);
        displayT.setText(title);
        displayQ.setText(question);

        Call<List<Responses>> call = retrofitClient.getInstance()
                .getApi().getResponses();
        call.enqueue(new Callback<List<Responses>>() {
            @Override
            public void onResponse(Call<List<Responses>> call, Response<List<Responses>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(displayQuestions.this,response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                List<Responses> responsesList = response.body();
                responseAdapter responseAdapter = new responseAdapter(displayQuestions.this,responsesList);
                responseRecycler.setAdapter(responseAdapter);
            }

            @Override
            public void onFailure(Call<List<Responses>> call, Throwable t) {
                Toast.makeText(displayQuestions.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(displayQuestions.this);
        View mView = getLayoutInflater().inflate(R.layout.layout_response_dialog,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        
        TextInputEditText Etresponse = (TextInputEditText) mView.findViewById(R.id.etEnterResponse);
        Button respond = (Button) mView.findViewById(R.id.btnRespond);
        respond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ResponseText = Etresponse.getText().toString();
                Call<ResponseBody> call = retrofitClient.getInstance()
                        .getApi().respond(author,title,ResponseText);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(displayQuestions.this,response.code(),Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {
                            assert response.body() != null;
                            String s = response.body().string();
                            Toast.makeText(displayQuestions.this,s,Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(displayQuestions.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                dialog.cancel();
            }
        });

    }
}