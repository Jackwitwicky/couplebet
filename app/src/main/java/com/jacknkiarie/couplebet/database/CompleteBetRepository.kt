package com.jacknkiarie.couplebet.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.jacknkiarie.couplebet.models.Bet

class CompleteBetRepository constructor(private val application : Application, private val betDao : BetDao) {

    val allBets : LiveData<List<Bet>> = betDao.getAllBets()

    suspend fun insert(bet : Bet) {
        betDao.insert(bet)
    }

    suspend fun findBet(uid : Int) : LiveData<Bet> {
       return betDao.findBet(uid)
    }
}