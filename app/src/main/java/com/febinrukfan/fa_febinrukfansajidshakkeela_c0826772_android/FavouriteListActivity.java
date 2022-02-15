package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.databinding.ActivityFavouriteListBinding;
import com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class FavouriteListActivity extends AppCompatActivity {

    ActivityFavouriteListBinding binding;
    RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavouriteListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        PlacesRoomDb database = PlacesRoomDb.getInstance(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvPlaces.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(this, database.placesDao().getAllPlaces());
        binding.rvPlaces.setAdapter(recyclerViewAdapter);
    }
}