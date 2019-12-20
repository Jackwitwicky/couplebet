package com.jacknkiarie.couplebet.ui.newbet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NewBetViewModelProviderFactory implements ViewModelProvider.Factory {

    private Application mApplication;

    public NewBetViewModelProviderFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewBetViewModel(mApplication);
    }
}
