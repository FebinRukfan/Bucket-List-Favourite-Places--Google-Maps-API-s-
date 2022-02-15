package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android;// Created by FebinRukfan on 15-02-2022.

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<Places> placesArrayList;
    public RecyclerViewAdapter(Context context, List<Places> placesArrayList) {
        this.context=context;
        this.placesArrayList=placesArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view =    LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.places_row_item, viewGroup, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.pName.setText(placesArrayList.get(position).getPlaces_name());
        holder.pDate.setText("Added Date: "+placesArrayList.get(position).getAdded_date());
        if(placesArrayList.get(position).getPlaces_visited()==true){
            holder.pVisited.setBackgroundColor(context.getColor(R.color.mild_green));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return placesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pName, pDate, pVisited;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pName = itemView.findViewById(R.id.placeName);
            pDate = itemView.findViewById(R.id.placeDate);
            pVisited = itemView.findViewById(R.id.txtVisited);

        }
    }
}
