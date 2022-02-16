package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.adapter;// Created by FebinRukfan on 15-02-2022.

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.R;
import com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.models.Places;
import com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.ui.PlacesInfoActivity;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Activity context;
    List<Places> placesList;
    private final String TAG = this.getClass().getName();

    public RecyclerViewAdapter(Activity context, List<Places> placesList) {
        this.context=context;
        this.placesList=placesList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view =    LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.pName.setText(placesList.get(position).getPlaces_name());
        holder.pDate.setText("Added Date: "+placesList.get(position).getAdded_date());
        
        //if he/she visited the place the strip changes it color to green
        if(placesList.get(position).getPlaces_visited()==true){
            holder.pVisited.setBackgroundColor(context.getColor(R.color.mild_green));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent places = new Intent(context, PlacesInfoActivity.class);
                places.putExtra("id",String.valueOf(placesList.get(position).getId()));
                Log.v(TAG,"here  " + placesList.get(position).getId());

                context.startActivity(places);
            }
        });



    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pName, pDate, pVisited;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pName = itemView.findViewById(R.id.placeName);
            pDate = itemView.findViewById(R.id.placeDate);
            pVisited = itemView.findViewById(R.id.txtVisited);

        }
    }
}
