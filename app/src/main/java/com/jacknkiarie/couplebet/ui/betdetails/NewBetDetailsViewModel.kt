package com.jacknkiarie.couplebet.ui.betdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jacknkiarie.couplebet.database.BetRoomDatabase
import com.jacknkiarie.couplebet.database.CompleteBetRepository
import com.jacknkiarie.couplebet.models.Bet
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewBetDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : CompleteBetRepository
    val allBets : LiveData<List<Bet>>
    var currentBet : LiveData<Bet>? = null

    init {
        val betDao = BetRoomDatabase.getDatabase(application, viewModelScope).betDao()
        repository = CompleteBetRepository(application, betDao)
        allBets = repository.allBets
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on the mainthread, blocking
     * the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called viewModelScope which we
     * can use here.
     */
    fun insert(bet: Bet) = viewModelScope.launch {
        repository.insert(bet)
    }

    fun findBet(uid: Int) = viewModelScope.launch {
        currentBet = repository.findBet(uid)
    }

}