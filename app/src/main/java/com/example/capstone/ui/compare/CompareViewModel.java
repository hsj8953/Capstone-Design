package com.example.capstone.ui.compare;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CompareViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CompareViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is compare fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}