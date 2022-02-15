package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android;// Created by FebinRukfan on 10-02-2022.


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public abstract class PlacesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(Places places);
//
//    @Query("SELECT * FROM product_table ORDER BY product_name ASC")
//    public abstract LiveData<List<Product>> getAllProducts();
//
//    @Query("SELECT * FROM product_table WHERE product_id=:id")
//    public abstract LiveData<List<Product>> getProductById(int id);
//
//    @Query("SELECT * FROM product_table ORDER BY product_id ASC limit 1")
//    public abstract LiveData<List<Product>> getFirstProduct();
//
    @Query("DELETE FROM places_table")
    public abstract void deleteAll();
//
//   @Query("DELETE FROM product_table WHERE product_id=:id")
//    public abstract void deletebyid(int id);
//
//
//    @Query("UPDATE product_table SET product_name=:name,product_desc=:desc,product_price=:price,provider_lat=:lat,provider_long=:lon WHERE product_id =:id")
//    public abstract void update(int id, String name, String desc, Double price, Double lat, Double lon);
}
