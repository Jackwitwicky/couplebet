package com.jacknkiarie.couplebet.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jacknkiarie.couplebet.models.Bet

@Dao
interface BetDao {

    // method to insert data into the system
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bet: Bet)

    // update multiple bets
    @Update
    abstract fun updateBets(vararg bets: Bet)

    // delete all bets from the app
    @Query("DELETE FROM bet_table")
    abstract fun deleteAll()

    // get list of bets
    @Query("SELECT * FROM bet_table")
    fun getAllBets(): LiveData<List<Bet>>

    // get a particular bet
    @Query("SELECT * FROM bet_table WHERE uid = :uid")
    fun findBet(uid: Int): LiveData<Bet>
}