package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.db;// Created by FebinRukfan on 10-02-2022.


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.models.Places;

import java.util.List;


@Dao
public abstract class PlacesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(Places places);

    @Query("SELECT * FROM places_table ORDER BY id ASC")
    public abstract List<Places> getAllPlaces();

    @Query("DELETE FROM places_table")
    public abstract void deleteAll();

    @Query("DELETE FROM places_table WHERE id=:pId")
    public abstract void deleteById(Long pId);

    @Query("SELECT places_id FROM places_table WHERE places_id=:place_id")
    public abstract String  checkPlaceId(String place_id);//

    @Query("SELECT places_name FROM places_table WHERE id=:pid")
    public abstract String  getPlaceName(Long pid);

    @Query("SELECT * FROM places_table WHERE id=:pid")
    public abstract Places getPlaceDetails(Long pid);

    @Query("UPDATE places_table SET places_name=:name,places_address=:address,places_latitude=:lat,places_longitude=:lon,added_date=:date,places_visited=:visit WHERE id =:id")
    public abstract void update(Long id, String name, String address, Double lat, Double lon,String date,Boolean visit);
}
