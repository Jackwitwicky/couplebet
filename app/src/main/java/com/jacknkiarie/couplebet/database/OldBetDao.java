package com.jacknkiarie.couplebet.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jacknkiarie.couplebet.models.Bet;

import java.util.List;

@Dao
public interface OldBetDao {

    // method to insert data into the system
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Bet bet);

    // update multiple bets
    @Update
    public void updateBets(Bet... bets);

    // delete all bets from the app
    @Query("DELETE FROM bet_table")
    void deleteAll();

    // get list of bets
    @Query("SELECT * FROM bet_table")
    LiveData<List<Bet>> getAllBets();

    // get a particular bet
    @Query("SELECT * FROM bet_table WHERE uid LIKE :uid")
    public LiveData<Bet> findBet(int uid);
}
