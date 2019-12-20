package com.jacknkiarie.couplebet.ui.newbet;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jacknkiarie.couplebet.database.BetRepository;
import com.jacknkiarie.couplebet.models.Bet;

import java.util.List;

public class NewBetViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private BetRepository betRepository;

    private LiveData<List<Bet>> allBets;

    public NewBetViewModel() {}

    public NewBetViewModel(Application application) {

        betRepository = new BetRepository(application);
        allBets = betRepository.getAllBets();

        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void insert(Bet bet) {betRepository.insert(bet);}
}