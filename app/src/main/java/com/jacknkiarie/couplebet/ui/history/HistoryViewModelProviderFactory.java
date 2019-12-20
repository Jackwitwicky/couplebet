package com.jacknkiarie.couplebet.ui.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jacknkiarie.couplebet.ui.history.HistoryViewModel;

public class HistoryViewModelProviderFactory implements ViewModelProvider.Factory {

    private Application mApplication;

    public HistoryViewModelProviderFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HistoryViewModel(mApplication);
    }
}
