package com.jacknkiarie.couplebet.ui.history;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jacknkiarie.couplebet.database.BetRepository;
import com.jacknkiarie.couplebet.models.Bet;

import java.util.List;

public class HistoryViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    private LiveData<List<Bet>> allBets;
    private BetRepository betRepository;

//    public HistoryViewModel() {}

    public HistoryViewModel(Application application) {
        mText = new MutableLiveData<>();
        mText.setValue("");

        betRepository = new BetRepository(application);
        allBets = betRepository.getAllBets();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Bet>> getAllBets() { return allBets; }
}
