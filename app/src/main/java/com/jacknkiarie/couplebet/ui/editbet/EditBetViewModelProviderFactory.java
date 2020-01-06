package com.jacknkiarie.couplebet.ui.editbet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jacknkiarie.couplebet.ui.newbet.NewBetViewModel;

public class EditBetViewModelProviderFactory implements ViewModelProvider.Factory {

    private Application mApplication;

    public EditBetViewModelProviderFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EditBetViewModel(mApplication);
    }
}
