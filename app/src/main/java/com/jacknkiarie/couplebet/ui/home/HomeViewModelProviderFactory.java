package com.jacknkiarie.couplebet.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jacknkiarie.couplebet.ui.history.HistoryViewModel;

public class HomeViewModelProviderFactory implements ViewModelProvider.Factory {

    private Application mApplication;

    public HomeViewModelProviderFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HomeViewModel(mApplication);
    }
}
