package com.jacknkiarie.couplebet.ui.betdetails;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jacknkiarie.couplebet.database.BetRepository;
import com.jacknkiarie.couplebet.models.Bet;

public class BetDetailsViewModel extends ViewModel {

    private BetRepository betRepository;
    private LiveData<Bet> currentBet;
    private MutableLiveData<String> title;

    public BetDetailsViewModel(Application application, int betuid) {
        betRepository = new BetRepository(application);
        currentBet = betRepository.getBet(betuid);

        title = new MutableLiveData<>();
//        title.setValue(currentBet.getValue().getTitle());
    }


    public LiveData<String> getTitle() {
        return title;
    }
//    public LiveData<String>

    public LiveData<Bet> getCurrentBet() { return currentBet; }

    public void insert(Bet bet) {betRepository.insert(bet);}

}
