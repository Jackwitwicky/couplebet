package com.jacknkiarie.couplebet.ui.betdetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BetDetailsViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;

    public BetDetailsViewModelFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewBetDetailsViewModel(mApplication);
    }
}
