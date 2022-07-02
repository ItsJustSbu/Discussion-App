package com.example.App2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    FloatingActionButton FButton;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    RecyclerView rv;
    Adapter adapter;
    List<PostModel> PostModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        rv.setLayoutManager(layoutManager);

        loadPosts();
        PostModelList = new ArrayList<>();


        FButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(HomeActivity.this);
                View customLayout = getLayoutInflater().inflate(R.layout.dialog_post,null);
                builder.setView(customLayout);
                AlertDialog dialog = builder.create();
                dialog.show();
                Button postBtn = (Button) customLayout.findViewById(R.id.PostButton);
                TextInputEditText title = (TextInputEditText) customLayout.findViewById(R.id.Title);
                TextInputEditText post = (TextInputEditText) customLayout.findViewById(R.id.Post);
                postBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String PostTitle = title.getText().toString().trim();
                        String DiscussionPost = post.getText().toString().trim();
                        if(TextUtils.isEmpty(PostTitle)){
                            title.setError("Title cannot be empty");
                        }else if(TextUtils.isEmpty(DiscussionPost)){
                            post.setError("Post cannot be empty");
                        }
                        else{
                            uploadData(PostTitle,DiscussionPost);
                            dialog.cancel();


                        }
                    }
                });
            }
        });
    }

    private void loadPosts() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostModelList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    PostModel postModel = ds.getValue(PostModel.class);
                    PostModelList.add(postModel);
                    adapter = new Adapter(HomeActivity.this, PostModelList);
                    rv.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadData(String postTitle, String discussionPost) {
        progressDialog.setMessage("publishing post");
        progressDialog.show();
        //Get time
        final String timeStamp = String.valueOf(System.currentTimeMillis());
        FirebaseUser user = auth.getCurrentUser();

        HashMap<String, Object> hashMap = new HashMap<>();
        assert user != null;
        hashMap.put("uid", user.getUid());
        hashMap.put("uEmail",user.getEmail());
        hashMap.put("upId",timeStamp);
        hashMap.put("post",discussionPost);
        hashMap.put("pTitle",postTitle);

        //posting data to firebase
        DatabaseReference reg = FirebaseDatabase.getInstance().getReference("posts");
        reg.child(timeStamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(HomeActivity.this, "Post Published", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}