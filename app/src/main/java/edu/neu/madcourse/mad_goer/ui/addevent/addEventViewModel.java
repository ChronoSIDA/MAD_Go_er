package edu.neu.madcourse.mad_goer.ui.addevent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class addEventViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public addEventViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}