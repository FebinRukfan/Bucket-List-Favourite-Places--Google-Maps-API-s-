package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.databinding.ActivityFavouriteListBinding;
import com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class FavouriteListActivity extends AppCompatActivity {

    ActivityFavouriteListBinding binding;
    RecyclerViewAdapter recyclerViewAdapter;

    RecyclerTouchListener touchListener;

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


        touchListener = new RecyclerTouchListener(this,binding.rvPlaces);
        touchListener
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
//                        Toast.makeText(getApplicationContext(),taskList.get(position).getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {

                    }
                })
                .setSwipeOptionViews(R.id.delete_task,R.id.edit_task)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        switch (viewID){
                            case R.id.delete_task:
                                database.placesDao().deleteById(database.placesDao().getAllPlaces().get(position).getId());
                                recyclerViewAdapter = new RecyclerViewAdapter(FavouriteListActivity.this, database.placesDao().getAllPlaces());
                                binding.rvPlaces.setAdapter(recyclerViewAdapter);
                                break;
                            case R.id.edit_task:

                                Intent places = new Intent(FavouriteListActivity.this, PlacesInfoActivity.class);
                                places.putExtra("id",String.valueOf(database.placesDao().getAllPlaces().get(position).getId()));
                                places.putExtra("edit","edit_mode");
                                startActivity(places);

                                break;

                        }
                    }
                });
        binding.rvPlaces.addOnItemTouchListener(touchListener);

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