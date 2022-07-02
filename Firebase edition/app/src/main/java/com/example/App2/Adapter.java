package com.example.App2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myHolder> {
    public Adapter(Context context, List<PostModel>postModelList){
        this.context = context;
        this.postModelList = postModelList;
    }

    Context context;
    List<PostModel> postModelList;
    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post, parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        PostModel p = postModelList.get(position);
        String title = p.getpTitle();
        String post = p.getPost();
        String author = p.getuId();
        holder.Title.setText("Title: "+title);
        holder.Author.setText("Author: "+author);
        holder.Post.setText("Post: "+post);
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    class myHolder extends RecyclerView.ViewHolder {
        TextView Title, Author, Post;
        public myHolder(@NonNull View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.textViewTitle);
            Author = (TextView) itemView.findViewById(R.id.textViewAuthor);
            Post = (TextView) itemView.findViewById(R.id.textViewPost);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent postDetailActivity = new Intent(context, PostDetailActivity.class);
                    int position = getAdapterPosition();

                    postDetailActivity.putExtra("title",postModelList.get(position).getpTitle());
                    postDetailActivity.putExtra("Post",postModelList.get(position).getpTitle());
                    context.startActivity(postDetailActivity);

                }
            });
        }
    }
}
