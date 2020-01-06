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
    @Query("SELECT * FROM bet_table ORDER BY uid DESC")
    LiveData<List<Bet>> getAllBets();

    // get list of complete bets
    @Query("SELECT * FROM bet_table WHERE status='completed'")
    LiveData<List<Bet>> getCompleteBets();

    // get list of ongoing bets
    @Query("SELECT * FROM bet_table WHERE status='ongoing'")
    LiveData<List<Bet>> getOngoingBets();

    // get a particular bet
    @Query("SELECT * FROM bet_table WHERE uid LIKE :uid")
    public LiveData<Bet> findBet(int uid);
}
