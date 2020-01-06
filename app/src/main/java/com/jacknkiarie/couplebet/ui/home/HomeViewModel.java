package com.jacknkiarie.couplebet.ui.home;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jacknkiarie.couplebet.database.BetRepository;
import com.jacknkiarie.couplebet.models.Bet;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private LiveData<List<Bet>> ongoingBets;
    private BetRepository betRepository;

    public HomeViewModel(Application application) {
        mText = new MutableLiveData<>();
        mText.setValue("");

        betRepository = new BetRepository(application);
        ongoingBets = betRepository.getOngoingBets();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Bet>> getOngoingBets() { return ongoingBets; }

    public void insert(Bet bet) {betRepository.insert(bet);}
}