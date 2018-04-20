package com.example.appinventiv.infoguard;

import android.app.Application;
import com.example.appinventiv.infoguard.data.DatabaseManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.init(this);
    }
}