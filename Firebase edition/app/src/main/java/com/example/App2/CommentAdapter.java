package com.example.App2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.myHolder> {

    public CommentAdapter(Context context, List<CommentModel> commentModelList){
        this.context = context;
        this.commentModelList = commentModelList;
    }
    Context context;
    List<CommentModel> commentModelList;
    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment, parent,false);
        return new CommentAdapter.myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        CommentModel c = commentModelList.get(position);
        String comment = c.getComment();
        holder.Comment.setText(comment);

    }

    @Override
    public int getItemCount() {
        return commentModelList.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {

        TextView Comment;
        public myHolder(@NonNull View itemView) {
            super(itemView);
           Comment = (TextView) itemView.findViewById(R.id.textViewComment);
        }
    }
}
