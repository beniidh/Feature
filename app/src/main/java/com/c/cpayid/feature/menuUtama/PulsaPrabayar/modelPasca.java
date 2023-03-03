package com.c.cpayid.feature.menuUtama.PulsaPrabayar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class modelPasca extends ViewModel {

    private MutableLiveData<String> iconurl;

    public void init()
    {
        iconurl = new MutableLiveData<>();
    }

    public void sendIconurl(String icon)
    {
        iconurl.setValue(icon);
    }
    public LiveData<String> getIconurl()
    {
        return iconurl;
    }

}
