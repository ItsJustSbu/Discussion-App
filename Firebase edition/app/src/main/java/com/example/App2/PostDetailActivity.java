package com.example.App2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class PostDetailActivity extends AppCompatActivity {

    TextView detail_title;
    TextView detail_post;
    EditText editTextComment;
    Button btnComment;
    RecyclerView rv;
    CommentAdapter commentAdapter;
    List<CommentModel> commentModelList;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        btnComment = (Button) findViewById(R.id.post_detail_add_comment_btn);
        editTextComment = (EditText) findViewById(R.id.post_detail_comment);
        detail_title = (TextView) findViewById(R.id.post_detail_title);
        detail_post = (TextView) findViewById(R.id.post_detail_desc);
        String PostTitle = getIntent().getExtras().getString("title");
        detail_title.setText(PostTitle);
        String Post = getIntent().getExtras().getString("Post");
        detail_post.setText(Post);
        rv = (RecyclerView) findViewById(R.id.rv_comment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        loadComment();
        commentModelList = new ArrayList<>();

        rv.setLayoutManager(layoutManager);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = editTextComment.getText().toString();
                if(TextUtils.isEmpty(comment)){
                    editTextComment.setError("Comment cannot be empty");
                }
                else{
                    postComment(PostTitle,Post,comment);
                }
            }
        });
    }
    private void postComment(String Title, String Post,String comment) {
        final String timeStamp = String.valueOf(System.currentTimeMillis());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", user.getUid());
        hashMap.put("comment", comment);
        hashMap.put("title",Title);
        hashMap.put("post", Post);
        DatabaseReference reg = FirebaseDatabase.getInstance().getReference("comments");
        reg.child(timeStamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void loadComment() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("comments");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentModelList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    CommentModel commentModel= ds.getValue(CommentModel.class);
                    commentModelList.add(commentModel);
                    commentAdapter = new CommentAdapter(PostDetailActivity.this, commentModelList);
                    rv.setAdapter(commentAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PostDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}