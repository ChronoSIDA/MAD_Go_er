package edu.neu.madcourse.mad_goer.ui.go;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}