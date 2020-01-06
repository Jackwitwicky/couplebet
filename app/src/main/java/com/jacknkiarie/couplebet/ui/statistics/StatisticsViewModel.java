package com.jacknkiarie.couplebet.ui.statistics;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jacknkiarie.couplebet.database.BetRepository;
import com.jacknkiarie.couplebet.models.Bet;

import java.util.List;

public class StatisticsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private LiveData<List<Bet>> completeBets;
    private BetRepository betRepository;

    public StatisticsViewModel(Application application) {
        mText = new MutableLiveData<>();
        mText.setValue("");

        betRepository = new BetRepository(application);
        completeBets = betRepository.getCompleteBets();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Bet>> getCompleteBets() { return completeBets; }
}