package com.valisha.myattendance.apps;

import android.app.Application;

import androidx.annotation.NonNull;

import com.valisha.myattendance.contracts.Preferences;
import com.valisha.myattendance.model.Db;
import com.valisha.myattendance.network.WebService;
import com.valisha.myattendance.network.WebServiceImpl;


public class App extends Application implements IApp {

    private IApp app;
    private Preferences appPreferences;

    private Db db;
    private WebService webService;



    @Override
    public void onCreate() {
        super.onCreate();
        app = ((IApp)getApplicationContext());



    }


    @NonNull
    @Override
    public Preferences preference() {
        if(appPreferences==null) appPreferences = new AppPreferences(this);
        return appPreferences;
    }

    @NonNull
    @Override
    public Db db() {
        if(db==null)db = Db.getInstance(this);
        return db;
    }

    @NonNull
    @Override
    public WebService webService() {
        if(webService==null)webService = new WebServiceImpl();
        return webService;
    }
}
