package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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


        getSupportActionBar().setTitle("Your Favourites!!");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PlacesRoomDb database = PlacesRoomDb.getInstance(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvPlaces.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(this, database.placesDao().getAllPlaces());
        binding.rvPlaces.setAdapter(recyclerViewAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}