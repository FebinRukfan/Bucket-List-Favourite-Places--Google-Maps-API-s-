package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android;// Created by FebinRukfan on 10-02-2022.


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;


@Dao
public abstract class PlacesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(Places places);

    @Query("SELECT * FROM places_table ORDER BY places_name ASC")
    public abstract List<Places> getAllPlaces();
//
//    @Query("SELECT * FROM product_table WHERE product_id=:id")
//    public abstract LiveData<List<Product>> getProductById(int id);
//
//    @Query("SELECT * FROM product_table ORDER BY product_id ASC limit 1")
//    public abstract LiveData<List<Product>> getFirstProduct();
//
    @Query("DELETE FROM places_table")
    public abstract void deleteAll();

    @Query("SELECT places_id FROM places_table WHERE places_id=:place_id")
    public abstract String  checkPlaceId(String place_id);//

    @Query("SELECT places_name FROM places_table WHERE id=:place_id")
    public abstract String  getPlaceName(String place_id);

    @Query("SELECT * FROM places_table WHERE id=:place_id")
    public abstract Places  getPlaceDetails(String place_id);
//
//    @Query("UPDATE product_table SET product_name=:name,product_desc=:desc,product_price=:price,provider_lat=:lat,provider_long=:lon WHERE product_id =:id")
//    public abstract void update(int id, String name, String desc, Double price, Double lat, Double lon);
}
