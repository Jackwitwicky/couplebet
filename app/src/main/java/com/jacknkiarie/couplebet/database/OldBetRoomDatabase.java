package com.jacknkiarie.couplebet.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jacknkiarie.couplebet.models.Bet;

@Database(entities =  {Bet.class}, version = 2)
public abstract class OldBetRoomDatabase extends RoomDatabase {

    public abstract OldBetDao betDao();

    private static OldBetRoomDatabase INSTANCE;

    public static OldBetRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (OldBetRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        OldBetRoomDatabase.class,
                        "bet_database")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }

        return INSTANCE;
    }
}
