package com.jacknkiarie.couplebet.database;
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//import com.jacknkiarie.couplebet.models.Bet;
//
//@Database(entities =  {Bet.class}, version = 2)
//public abstract class BetRoomDatabase extends RoomDatabase {
//
//    public abstract BetDao betDao();
//
//    private static BetRoomDatabase INSTANCE;
//
//    public static BetRoomDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (BetRoomDatabase.class) {
//                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                        BetRoomDatabase.class,
//                        "bet_database")
//                        .fallbackToDestructiveMigration()
//                        .build();
//            }
//        }
//
//        return INSTANCE;
//    }
//}


/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//package com.example.android.roomwordssample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jacknkiarie.couplebet.database.BetDao
import com.jacknkiarie.couplebet.models.Bet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.  In a real
 * app, consider exporting the schema to help you with migrations.
 */
@Database(entities = [Bet::class], version = 2, exportSchema = false)
public abstract class BetRoomDatabase : RoomDatabase() {

        abstract fun betDao(): BetDao

        companion object {
                @Volatile
                private var INSTANCE: BetRoomDatabase? = null

                fun getDatabase(
                        context: Context,
                        scope: CoroutineScope
                ): BetRoomDatabase {
                        // if the INSTANCE is not null, then return it,
                        // if it is, then create the database
                        return INSTANCE ?: synchronized(this) {
                                val instance = Room.databaseBuilder(
                                        context.applicationContext,
                                        BetRoomDatabase::class.java,
                                        "bet_database"
                                )
                                        .addCallback(WordDatabaseCallback(scope))
                                        .build()
                                INSTANCE = instance
                                // return instance
                                instance
                        }
                }

                private class WordDatabaseCallback(
                        private val scope: CoroutineScope
                ) : RoomDatabase.Callback() {
                        /**
                         * Override the onOpen method to populate the database.
                         * For this sample, we clear the database every time it is created or opened.
                         */
                        override fun onOpen(db: SupportSQLiteDatabase) {
                                super.onOpen(db)
                                // If you want to keep the data through app restarts,
                                // comment out the following line.
//                                INSTANCE?.let { database ->
//                                        scope.launch(Dispatchers.IO) {
//                                                populateDatabase(database.wordDao())
//                                        }
//                                }
                        }
                }
        }

}

