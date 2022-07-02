package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ipsec.ike.TunnelModeChildSessionParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        List<Data> DataList;
        Context context;

    public MyAdapter(Context ct,List<Data> myData){
        context = ct;
        DataList = myData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Data data = DataList.get(position);
        holder.Title.setText(data.getTitle());
        holder.Question.setText(data.getQuestion());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(context,displayQuestions.class);
              intent.putExtra("Title",data.getTitle());
              intent.putExtra("Question",data.getQuestion());
              intent.putExtra("Author",data.getAuthor());
              context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Title, Question;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemsView){
            super(itemsView);
            Title = itemsView.findViewById(R.id.Title);
            Question = itemsView.findViewById(R.id.Description);
            mainLayout = itemsView.findViewById(R.id.mainLayout);
        }
    }
}
