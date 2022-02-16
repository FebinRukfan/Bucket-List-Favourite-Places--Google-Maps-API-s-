package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.db;
// Created by FebinRukfan on 14-02-2022.

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.models.Places;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Places.class}, version = 1, exportSchema = false)
public abstract class PlacesRoomDb extends RoomDatabase {

    public abstract PlacesDao placesDao();

    private static volatile PlacesRoomDb INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    // executor service helps to do tasks in background thread
    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PlacesRoomDb getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlacesRoomDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PlacesRoomDb.class,
                            "places_database")
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final Callback sRoomDatabaseCallback =
            new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    databaseWriteExecutor.execute(() -> {
                        PlacesDao productDao = INSTANCE.placesDao();
                        productDao.deleteAll();

                    });
                }
            };
}
