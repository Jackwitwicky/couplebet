package com.jacknkiarie.couplebet.ui.editbet;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jacknkiarie.couplebet.database.BetRepository;
import com.jacknkiarie.couplebet.models.Bet;

import java.util.List;

public class EditBetViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private BetRepository betRepository;

    private LiveData<List<Bet>> allBets;

    public EditBetViewModel() {}

    public EditBetViewModel(Application application) {

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