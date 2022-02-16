package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.adapter;// Created by FebinRukfan on 15-02-2022.

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.R;
import com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.models.Nearby;

import java.util.ArrayList;
import java.util.List;

public class NearbyRVAdapter extends RecyclerView.Adapter<NearbyRVAdapter.ViewHolder> {
    Context context;
    ArrayList<Nearby> rowItems;
    private final String TAG = this.getClass().getName();


    public NearbyRVAdapter(Context context, ArrayList<Nearby> items) {
        this.context = context;
        this.rowItems = items;
    }


    @NonNull
    @Override
    public NearbyRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view =    LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
        return new NearbyRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearbyRVAdapter.ViewHolder holder, int position) {




        holder.txtName.setText(rowItems.get(position).getnName());
        holder.imgView.setImageResource(rowItems.get(position).getnImg());



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        ImageView imgView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtNname);
            imgView = itemView.findViewById(R.id.imageView);

        }
    }
}
