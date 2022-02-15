package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android;// Created by FebinRukfan on 15-02-2022.

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "places_table"

)
public class Places {


    @NonNull
    public String getPlaces_name() {
        return places_name;
    }

    public void setPlaces_name(@NonNull String places_name) {
        this.places_name = places_name;
    }

    public Boolean getPlaces_visited() {
        return places_visited;
    }

    public void setPlaces_visited(Boolean places_visited) {
        this.places_visited = places_visited;
    }

    public String getPlaces_address() {
        return places_address;
    }

    public void setPlaces_address(String places_address) {
        this.places_address = places_address;
    }

    public Double getPlaces_latitude() {
        return places_latitude;
    }

    public void setPlaces_latitude(Double places_latitude) {
        this.places_latitude = places_latitude;
    }

    public Double getPlaces_longitude() {
        return places_longitude;
    }

    public void setPlaces_longitude(Double places_longitude) {
        this.places_longitude = places_longitude;
    }

    public String getAdded_date() {
        return added_date;
    }

    public void setAdded_date(String added_date) {
        this.added_date = added_date;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @NonNull
    @ColumnInfo(name = "places_name")
    private String places_name;

    @ColumnInfo(name = "places_visited")
    private Boolean places_visited;

    @ColumnInfo(name = "places_address")
    private String places_address;

    @ColumnInfo(name = "places_latitude")
    private Double places_latitude;

    @ColumnInfo(name = "places_longitude")
    private Double places_longitude;

    @ColumnInfo(name = "added_date")
    private String added_date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlaces_id() {
        return places_id;
    }

    public void setPlaces_id(String places_id) {
        this.places_id = places_id;
    }

    @ColumnInfo(name = "places_id")
    private String places_id;

    @Ignore
    public Places(){

    }
    public Places(long id, @NonNull String places_name, Boolean places_visited, String places_address,String places_id, Double places_latitude, Double places_longitude, String added_date) {
        this.id = id;
        this.places_name = places_name;
        this.places_visited = places_visited;
        this.places_address = places_address;
        this.places_id = places_id;
        this.places_latitude = places_latitude;
        this.places_longitude = places_longitude;
        this.added_date = added_date;
    }
}
