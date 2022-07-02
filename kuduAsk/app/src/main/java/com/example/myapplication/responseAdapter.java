package com.example.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class responseAdapter extends RecyclerView.Adapter<responseAdapter.MyViewHolder> {

    List<Responses> ResponseList;
    Context context;

    public responseAdapter(Context ct, List<Responses>responsesList){
        context = ct;
        ResponseList = responsesList;
    }

    @NonNull
    @Override
    public responseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.response_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull responseAdapter.MyViewHolder holder, int position) {
        Responses responses = ResponseList.get(position);
        holder.response.setText(responses.getResponse());
    }

    @Override
    public int getItemCount() {
       return ResponseList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView response;

        public MyViewHolder(@NonNull View itemsView){
            super(itemsView);
            response = itemsView.findViewById(R.id.displayResponses);
        }
    }
}
