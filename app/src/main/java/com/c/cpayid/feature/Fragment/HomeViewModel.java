package com.c.cpayid.feature.Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.c.cpayid.feature.Model.MBanner;
import com.c.cpayid.feature.Model.Micon;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<MBanner>> iconbanner;
    private MutableLiveData<ArrayList<Micon>> iconarray;
    private MutableLiveData<String> paylater;
    private MutableLiveData<String> payyletter;
    private MutableLiveData<String> saldoku;
    private MutableLiveData<String> running;

    public void init()
    {
        iconbanner=new MutableLiveData<>();
        iconarray = new MutableLiveData<>();
        paylater = new MutableLiveData<>();
        saldoku = new MutableLiveData<>();
        payyletter = new MutableLiveData<>();
        running  = new MutableLiveData<>();
    }

    public void sendDataIcon(ArrayList<Micon> iconn)
    {
        iconarray.postValue(iconn);
    }

    public void sendDataIconBanner(ArrayList<MBanner> mBanners)
    {
        iconbanner.postValue(mBanners);
    }
    public LiveData<ArrayList<Micon>> getIcon()
    {
        return iconarray;

    }
    public LiveData<ArrayList<MBanner>> getIconBanner()
    {
        return iconbanner;

    }
    public void sendPayLater(String status)
    {
        paylater.setValue(status);
    }

    public void sendRunning(String status)
    {
        running.setValue(status);
    }

    public LiveData<String> getRunning()
    {
        return running;
    }

    public LiveData<String> getPayLater()
    {
        return paylater;
    }

    public void sendSaldoku(String saldo)
    {
        saldoku.setValue(saldo);
    }
    public LiveData<String> getSaldoku()
    {
        return saldoku;
    }

    public void sendPayyLetter(String value)
    {
        payyletter.setValue(value);
    }
    public LiveData<String> getPayyLetter()
    {
        return payyletter;
    }


}